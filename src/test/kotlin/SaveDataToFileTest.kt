import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import pl.mothdigital.postdumper.Storage
import pl.mothdigital.postdumper.saveDataToFile

class SaveDataToFileTest {

    @Test
    fun `should save data using storage`() {
        val storage = mockk<Storage>()
        val data = "test data"
        val pathname = "test/path.json"

        every { storage.save(any(), any()) } returns Result.success(Unit)

        val result = saveDataToFile(storage, data, pathname)

        verify { storage.save(data, pathname) }
        assertTrue(result.isSuccess)
    }
}