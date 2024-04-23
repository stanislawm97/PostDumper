package pl.mothdigital.postdumper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun preparePostData(json: Json, post: Post): String {
    return json.encodeToString(post)
}

fun createDirectory(storage: Storage, name: String) {
    storage.createDirectory(name)
}

fun saveDataToFile(storage: Storage, json: Json, vararg posts: Post) {
    if (posts.size > 1) {
        saveDataToFile(storage, json, *posts.dropLast(1).toTypedArray())
    }

    val post = posts.last()
    val data = preparePostData(json, post)

    storage.save(data, "${Defaults.DIRECTORY_NAME}/${post.id}.json")
}

suspend fun fetchPosts(client: Remote): Result<List<Post>> {
    return runCatching {
        client.getPosts()
    }
}

suspend fun main() = coroutineScope {
    val remote: Remote = RemoteImpl()
    val storage: Storage = StorageImpl()
    val json = Json { prettyPrint = true }

    val posts: List<Post> = async(Dispatchers.IO) {
        fetchPosts(remote)
            .onFailure { it.printStackTrace() }
            .getOrDefault(emptyList())
    }.await()

    createDirectory(storage, Defaults.DIRECTORY_NAME)

    saveDataToFile(storage, json, *posts.toTypedArray())
}

object Defaults {
    const val DIRECTORY_NAME = "posts"
}