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
    }

    private fun initializeNewProjectFolder() {
        showMessage("=> 🐢 Initializing new project...")
        copyFiles(fromPath = rootPath + TEMPLATE_FOLDER_NAME, toPath = projectPath)
        // Set gradlew file as executable, because copying files from one folder to another doesn't copy file permissions correctly (= read, write & execute).
        File(projectPath + File.separator + "gradlew")?.setExecutable(true)
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

    private fun renamePackageNameFolders() {
        showMessage("=> 🔎 Rename the package folders...")
        modules.forEach { module ->
            val srcPath = projectPath + File.separator + module + File.separator + "src"
            File(srcPath)
                .walk()
                .maxDepth(2)
                .filter { it.isDirectory && it.name == "java" }
                .forEach { javaDirectory ->
                    val javaPath = javaDirectory.absolutePath
                    val currentPackagePath =
                        javaPath + File.separator + TEMPLATE_PACKAGE_NAME.replace(
                            DOT_SEPARATOR,
                            File.separator
                        )
                    val newPackagePath =
                        javaPath + File.separator + packageName.replace(
                            DOT_SEPARATOR,
                            File.separator
                        )
                    if (File(currentPackagePath).exists()) {
                        copyCurrentPackageToNewPackageAndClean(
                            currentPackagePath = currentPackagePath,
                            tempPath = javaPath + File.separator + "temp_folder",
                            newPackagePath = newPackagePath
                        )
                    }
                }
        }
    }

    private fun copyCurrentPackageToNewPackageAndClean(
        currentPackagePath: String,
        tempPath: String,
        newPackagePath: String
    ) {
        copyFiles(fromPath = currentPackagePath, toPath = tempPath)
        deleteFilesOrDirectories(
            currentPackagePath.replace(
                TEMPLATE_PACKAGE_NAME.replace(DOT_SEPARATOR, File.separator),
                TEMPLATE_PACKAGE_NAME.split(DOT_SEPARATOR).first()
            )
        )
        copyFiles(fromPath = tempPath, toPath = newPackagePath)
        deleteFilesOrDirectories(tempPath)
    }

    private fun deleteFilesOrDirectories(fromPath: String) {
        File(fromPath).deleteRecursively()
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

    private fun cleanNewProjectFolder() {
        executeCommand("sh $projectPath${File.separator}gradlew -p $projectPath clean")
        executeCommand("sh $projectPath${File.separator}gradlew -p $projectPath${File.separator}buildSrc clean")
        listOf(".idea", ".gradle", "buildSrc${File.separator}.gradle", ".git").forEach {
            File("$projectPath${File.separator}$it")?.let { targetFile ->
                targetFile.deleteRecursively()
            }
        }
    }

    private fun executeCommand(command: String) {
        val process = Runtime.getRuntime().exec(command)
        process.inputStream.reader().forEachLine { println(it) }
    }

    private fun renamePackageNameWithinFiles() {
        showMessage("=> 🔎 Renaming package name within files...")
        File(projectPath)
            ?.walk()
            .filter { it.isFile }
            .forEach { filePath ->
                rename(
                    sourcePath = filePath.toString(),
                    oldValue = TEMPLATE_PACKAGE_NAME,
                    newValue = packageName
                )
            }
    }

    private fun rename(sourcePath: String, oldValue: String, newValue: String) {
        val sourceFile = File(sourcePath)
        var sourceText = sourceFile.readText()
        sourceText = sourceText.replace(oldValue, newValue)
        sourceFile.writeText(sourceText)
    }
}

NewProject.generate(args)
