package pl.mothdigital.postdumper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.mothdigital.postdumper.common.Defaults
import pl.mothdigital.postdumper.model.Post

fun preparePostData(json: Json, post: Post): String {
    return json.encodeToString(post)
}

fun createDirectory(storage: Storage, name: String): Result<Unit> {
    return storage.createDirectory(name)
}

fun saveDataToFile(storage: Storage, data: String, pathname: String): Result<Unit> {
    return storage.save(data, pathname)
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

    posts.forEach { post ->
        val preparedData = preparePostData(json, post)
        saveDataToFile(storage, preparedData, "${Defaults.DIRECTORY_NAME}/${post.id}.json")
    }
}