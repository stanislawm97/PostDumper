package pl.mothdigital.postdumper

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import pl.mothdigital.postdumper.model.Post

interface Remote {
    suspend fun getPosts(): List<Post>
}

class RemoteImpl : Remote {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json(Json) }
    }

    override suspend fun getPosts(): List<Post> =
        client.get("$BASE_URL/posts").body()

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}