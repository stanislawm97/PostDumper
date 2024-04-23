import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pl.mothdigital.postdumper.Storage
import pl.mothdigital.postdumper.createDirectory

class CreateDirectoryTest {

    @Test
    fun `should call createDirectory on storage with correct name`() {
        val storage = mockk<Storage>(relaxed = true)
        val directoryName = "testDir"

        createDirectory(storage, directoryName)

        verify { storage.createDirectory(directoryName) }
    }
}