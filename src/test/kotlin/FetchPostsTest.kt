import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import pl.mothdigital.postdumper.model.Post
import pl.mothdigital.postdumper.Remote
import pl.mothdigital.postdumper.fetchPosts
import kotlin.test.Test
import kotlin.test.assertTrue

class FetchPostsTest {

    @Test
    fun `should fetch posts correctly`() = runBlocking {
        val remote = mockk<Remote>()
        val expectedPosts = listOf(Post(1, 1, "Test", "Content"))

        coEvery { remote.getPosts() } returns expectedPosts

        val result = fetchPosts(remote)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrDefault(emptyList()).size == 1)
    }
}