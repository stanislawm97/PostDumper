package pl.mothdigital.postdumper

import java.io.File

interface Storage {
    fun createDirectory(name: String): Result<Unit>
    fun save(data: String, pathname: String): Result<Unit>
}

class StorageImpl : Storage {
    override fun createDirectory(name: String): Result<Unit> {
        return runCatching {
            val directory = File(name)
            if (!directory.exists()) {
                directory.mkdirs()
            }
        }
    }

    override fun save(data: String, pathname: String): Result<Unit> {
        return runCatching { File(pathname).writeText(data) }
    }
}