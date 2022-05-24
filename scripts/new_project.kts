import java.io.File

object NewProject {

    private const val ARGUMENT_DELIMITER = "="
    private const val DOT_SEPARATOR = "."
    private const val KEY_APP_NAME = "app-name"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val TEMPLATE_APPLICATION_CLASS_NAME = "CoroutineTemplateApplication"
    private const val TEMPLATE_FOLDER_NAME = "CoroutineTemplate"
    private const val TEMPLATE_PACKAGE_NAME = "co.nimblehq.coroutine"

    private const val APP_PATTERN = "^[A-Z\$][a-zA-Z0-9(\\s)\$]*\$"
    private const val PACKAGE_PATTERN = "^[a-z]+(\\.[a-z][a-z0-9]*)*\$"

    private val modules = listOf("app", "data", "domain")
    private val fileSeparator = File.separator

    private var appName = ""
    private var packageName = ""

    private val applicationClassName: String
        get() = "${appNameWithoutSpace}Application"

    private val projectPath: String
        get() = rootPath + appName.replace(oldValue = " ", newValue = "")

    private val rootPath: String
        get() = System.getProperty("user.dir").replace("scripts", "")

    fun generate(args: Array<String>) {
        handleArguments(args)
    }

    private fun handleArguments(args: Array<String>) {
        val agrumentError = "ERROR: Invalid Agrument name: Ensure define argruments => app-name={\"MyProject\"} or {\"My Project\"} { package-name={com.sample.myproject}"
        when (args.size) {
            1 -> when {
                args.first().startsWith(KEY_APP_NAME) -> showMessage("ERROR: No package has been provided")
                args.first().startsWith(KEY_PACKAGE_NAME) -> showMessage("ERROR: No app name has been provided")
                else -> showMessage(agrumentError)
            }
            2 -> args.map { arg ->
                val (key, value) = arg.split(ARGUMENT_DELIMITER)
                when (key) {
                    KEY_APP_NAME -> validAppName(value)
                    KEY_PACKAGE_NAME -> validPackageName(value)
                    else -> showMessage(agrumentError)
                }.also { executeNextSteps() }
            }
            else -> showMessage("ERROR: Require app-name and package-name to initialize the new project")
        }
    }

    private fun validAppName(value: String) {
        if (APP_PATTERN.toRegex().containsMatchIn(value)) {
            appName = value
        } else {
            showMessage("ERROR: Invalid App Name: $value (needs to follow standard pattern {MyProject} or {My Project})")
        }
    }

    private fun validPackageName(value: String) {
        if (PACKAGE_PATTERN.toRegex().containsMatchIn(value)) {
            packageName = value
        } else {
            showMessage("ERROR: Invalid Package Name: $value (needs to follow standard pattern {com.example.package})")
        }
    }

    private fun executeNextSteps() {
        if (appName.isNotEmpty() && packageName.isNotEmpty()) {
            initializeNewProjectFolder()
            cleanNewProjectFolder()
            renamePackageNameFolders()
            renamePackageNameWithinFiles()
            renameApplicationClass()
            buildProjectAndRunTests()
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
                    copyFiles(
                        fromPath = oldDirectory.absolutePath,
                        toPath = tempDirectory.absolutePath
                    )
                    oldDirectory.parentFile?.parentFile?.deleteRecursively()
                    newDirectory.mkdirs()
                    copyFiles(
                        fromPath = tempDirectory.absolutePath,
                        toPath = newDirectory.absolutePath
                    )
                    tempDirectory.deleteRecursively()
                }
        }
    }

    private fun renamePackageNameWithinFiles() {
        showMessage("=> 🔎 Renaming package name within files...")
        File(projectPath)
            ?.walk()
            .filter { it.name.endsWith(".kt") || it.name.endsWith(".xml") }
            .forEach { filePath ->
                rename(
                    sourcePath = filePath.toString(),
                    oldValue = TEMPLATE_PACKAGE_NAME,
                    newValue = packageName
                )
            }
    }

    private fun renameApplicationClass() {
        showMessage("=> 🔎 Renaming application class...")
        File(projectPath)
            ?.walk()
            .filter { it.name == "$TEMPLATE_APPLICATION_CLASS_NAME.kt" || it.name == "AndroidManifest.xml" }
            .forEach { file ->
                rename(
                    sourcePath = file.absolutePath,
                    oldValue = TEMPLATE_APPLICATION_CLASS_NAME,
                    newValue = applicationClassName
                )
                if (file.name == "$TEMPLATE_APPLICATION_CLASS_NAME.kt") {
                    val newApplicationPath = file.absolutePath.replaceAfterLast(
                        delimiter = fileSeparator,
                        replacement = "$applicationClassName.kt"
                    )
                    val newApplicationFile = File(newApplicationPath)
                    file.renameTo(newApplicationFile)
                }
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
        val exitValue = process.waitFor()
        if (exitValue != 0) {
            showMessage("❌ Something went wrong!")
            System.exit(exitValue)
        }
    }

    private fun rename(sourcePath: String, oldValue: String, newValue: String) {
        val sourceFile = File(sourcePath)
        var sourceText = sourceFile.readText()
        sourceText = sourceText.replace(oldValue, newValue)
        sourceFile.writeText(sourceText)
    }

    private fun showMessage(message: String) {
        println("\n${message}\n")
    }
}

NewProject.generate(args)
