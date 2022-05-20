import java.io.File

object NewProject {

    private const val ARGUMENT_DELIMITER = "="
    private const val DOT_SEPARATOR = "."
    private const val KEY_APP_NAME = "app-name"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val TEMPLATE_FOLDER_NAME = "CoroutineTemplate"
    private const val TEMPLATE_PACKAGE_NAME = "co.nimblehq.coroutine"

    private val modules = listOf("app", "data", "domain")

    private var appName = ""
    private val appNameWithoutSpace: String
        get() = appName.replace(" ", "")
    private val fileSeparator = File.separator
    private var packageName = ""
    private val projectPath: String
        get() = rootPath + appNameWithoutSpace
    private val rootPath: String
        get() = System.getProperty("user.dir").replace("scripts", "")

    fun generate(args: Array<String>) {
        handleArguments(args)
        initializeNewProjectFolder()
        cleanNewProjectFolder()
        renamePackageNameFolders()
        renamePackageNameWithinFiles()
        buildProjectAndRunTests()
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

    private fun initializeNewProjectFolder() {
        showMessage("=> 🐢 Initializing new project...")
        copyFiles(fromPath = rootPath + TEMPLATE_FOLDER_NAME, toPath = projectPath)
        // Set gradlew file as executable, because copying files from one folder to another doesn't copy file permissions correctly (= read, write & execute).
        File(projectPath + fileSeparator + "gradlew")?.setExecutable(true)
    }

    private fun cleanNewProjectFolder() {
        executeCommand("sh $projectPath${fileSeparator}gradlew -p $projectPath clean")
        executeCommand("sh $projectPath${fileSeparator}gradlew -p $projectPath${fileSeparator}buildSrc clean")
        listOf(".idea", ".gradle", "buildSrc$fileSeparator.gradle", ".git").forEach {
            File("$projectPath$fileSeparator$it")?.let { targetFile ->
                targetFile.deleteRecursively()
            }
        }
    }

    private fun renamePackageNameFolders() {
        showMessage("=> 🔎 Renaming the package folders...")
        modules.forEach { module ->
            val srcPath = projectPath + fileSeparator + module + fileSeparator + "src"
            File(srcPath)
                .walk()
                .maxDepth(2)
                .filter { it.isDirectory && it.name == "java" }
                .forEach { javaDirectory ->
                    val oldDirectory = File(
                        javaDirectory, TEMPLATE_PACKAGE_NAME.replace(
                            oldValue = DOT_SEPARATOR,
                            newValue = fileSeparator
                        )
                    )
                    val newDirectory = File(
                        javaDirectory, packageName.replace(
                            oldValue = DOT_SEPARATOR,
                            newValue = fileSeparator
                        )
                    )

                    val tempDirectory = File(javaDirectory, "temp_directory")
                    copyFiles(fromPath = oldDirectory.absolutePath, toPath = tempDirectory.absolutePath)
                    oldDirectory.parentFile?.parentFile?.deleteRecursively()
                    newDirectory.mkdirs()
                    copyFiles(fromPath = tempDirectory.absolutePath, toPath = newDirectory.absolutePath)
                    tempDirectory.deleteRecursively()
                }
        }
    }

    private fun renamePackageNameWithinFiles() {
        showMessage("=> 🔎 Renaming package name within files...")
        File(projectPath)
            ?.walk()
            .filter { it.isFile && it.name != "debug.keystore" && it.name != "gradle-wrapper.jar"}
            .forEach { filePath ->
                rename(
                    sourcePath = filePath.toString(),
                    oldValue = TEMPLATE_PACKAGE_NAME,
                    newValue = packageName
                )
            }
    }

    private fun buildProjectAndRunTests() {
        showMessage("=> 🛠️ Building project...")
        executeCommand("sh $projectPath${fileSeparator}gradlew -p /$projectPath assembleDebug")
        showMessage("=> 🚓 Running tests...")
        executeCommand("sh $projectPath${fileSeparator}gradlew -p /$projectPath testStagingDebugUnitTest")
        showMessage("=> 🚀 Done! The project is ready for development")
    }

    private fun showMessage(message: String) {
        println("\n${message}\n")
    }

    private fun copyFiles(fromPath: String, toPath: String) {
        val targetFolder = File(toPath)
        val sourceFolder = File(fromPath)
        sourceFolder.copyRecursively(targetFolder, true) { file, exception ->
            showMessage(exception?.message ?: "Error copying files")
            return@copyRecursively OnErrorAction.TERMINATE
        }
    }

    private fun executeCommand(command: String) {
        val process = Runtime.getRuntime().exec(command)
        process.inputStream.reader().forEachLine { println(it) }
    }

    private fun rename(sourcePath: String, oldValue: String, newValue: String) {
        val sourceFile = File(sourcePath)
        var sourceText = sourceFile.readText()
        sourceText = sourceText.replace(oldValue, newValue)
        sourceFile.writeText(sourceText)
    }
}

NewProject.generate(args)
