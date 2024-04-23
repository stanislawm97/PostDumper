import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.mothdigital.postdumper.model.Post
import pl.mothdigital.postdumper.preparePostData

class PreparePostDataTest {

    @Test
    fun `should encode post to JSON correctly`() {
        val json = Json { prettyPrint = true }
        val post = Post(userId = 1, id = 1, title = "Test", body = "This is a test")

        val result = preparePostData(json, post)

        val expected = """
            {
                "userId": 1,
                "id": 1,
                "title": "Test",
                "body": "This is a test"
            }
        """.trimIndent()

        assertEquals(expected, result)
    }
}