import java.io.File

object NewProject {

    private const val ARGUMENT_DELIMITER = "="
    private const val KEY_APP_NAME = "app-name"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val TEMPLATE_FOLDER_NAME = "CoroutineTemplate"

    private var appName = ""
    private val appNameWithoutSpace: String
        get() = appName.replace(" ", "")
    private var packageName = ""

    fun generate(args: Array<String>) {
        handleArguments(args)
        initializeNewProjectFolder()
    }

    private fun initializeNewProjectFolder() {
        showMessage("=> 🐢 Initializing new project...")
        copyFiles(fromPath = TEMPLATE_FOLDER_NAME, toPath = appNameWithoutSpace)
        // Set gradlew file as executable, because copying files from one folder to another doesn't copy file permissions correctly (= read, write & execute).
        File("../$appNameWithoutSpace/gradlew")?.setExecutable(true)
    }

    private fun handleArguments(args: Array<String>) {
        args.forEach {
            val (key, value) = it.split(ARGUMENT_DELIMITER)
            when (key) {
                KEY_APP_NAME -> appName = value
                KEY_PACKAGE_NAME -> packageName = value
            }
        }
    }

    private fun showMessage(message: String) {
        println("\n${message}\n")
    }

    private fun copyFiles(fromPath: String, toPath: String) {
        val targetFolder = File("../$toPath")
        val sourceFolder = File("../$fromPath")
        sourceFolder.copyRecursively(targetFolder, true) { file, exception ->
            showMessage(exception?.message ?: "Error copying files")
            return@copyRecursively OnErrorAction.TERMINATE
        }
    }
}

NewProject.generate(args)
