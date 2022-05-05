import java.io.File
import java.util.*

fun File.loadGradleProperties(fileName: String): Properties {
    val properties = Properties()
    val signingProperties = File(this, fileName)

    if (signingProperties.isFile) {
        properties.load(signingProperties.inputStream())
    }
    return properties
}
