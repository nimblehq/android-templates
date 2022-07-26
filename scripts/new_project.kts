import java.io.File

object NewProject {

    private const val ARGUMENT_DELIMITER = "="
    private const val DOT_SEPARATOR = "."
    private const val KEY_APP_NAME = "app-name"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val MINUS_SEPARATOR = "-"
    private const val SPACE_SEPARATOR = " "
    private const val TEMPLATE_APP_NAME = "Template"
    private const val TEMPLATE_APPLICATION_CLASS_NAME = "TemplateApplication"
    private const val TEMPLATE_FOLDER_NAME = "template"
    private const val TEMPLATE_PACKAGE_NAME = "co.nimblehq.template"

    private const val PATTERN_APP = "^([A-Z][a-zA-Z0-9\\s]*)|([a-z][a-z0-9-]*)$"
    private const val PATTERN_PACKAGE = "^[a-z]+(\\.[a-z][a-z0-9]*)+$"

    private val modules = listOf("app", "data", "domain")
    private val fileSeparator = File.separator

    private var appName: String = ""
        set(value) {
            field = if (value.contains(MINUS_SEPARATOR)) {
                projectFolderName = value
                value.replace(MINUS_SEPARATOR, SPACE_SEPARATOR).uppercaseEveryFirstCharacter()
            } else {
                value.uppercaseEveryFirstCharacter().also {
                    projectFolderName = it.getStringWithoutSpace()
                }
            }
        }

    private var packageName = ""

    private val appNameWithoutSpace: String
        get() = appName.getStringWithoutSpace()

    private val applicationClassName: String
        get() = "${appNameWithoutSpace}Application"

    private var projectFolderName: String = ""

    private val projectPath: String
        get() = rootPath + projectFolderName

    private val rootPath: String
        get() = System.getProperty("user.dir").replace("scripts", "")

    fun generate(args: Array<String>) {
        handleArguments(args)
        initializeNewProjectFolder()
        cleanNewProjectFolder()
        renamePackageNameFolders()
        renamePackageNameWithinFiles()
        renameApplicationClass()
        renameAppName()
        buildProjectAndRunTests()
    }

    private fun handleArguments(args: Array<String>) {
        val argumentError = "ERROR: Invalid argument name: Ensure define arguments => app-name={\"MyProject\"} or {\"My Project\"} package-name={com.sample.myproject}"
        when (args.size) {
            1 -> when {
                args.first().startsWith(KEY_APP_NAME) -> showErrorMessage("ERROR: No package name has been provided")
                args.first().startsWith(KEY_PACKAGE_NAME) -> showErrorMessage("ERROR: No app name has been provided")
                else -> showErrorMessage(argumentError)
            }
            2 -> args.forEach { arg ->
                val (key, value) = arg.split(ARGUMENT_DELIMITER)
                when (key) {
                    KEY_APP_NAME -> validateAppName(value)
                    KEY_PACKAGE_NAME -> validatePackageName(value)
                    else -> showErrorMessage(argumentError)
                }
            }
            else -> showErrorMessage("ERROR: Require app-name and package-name to initialize the new project")
        }
    }

    private fun validateAppName(value: String) {
        if (PATTERN_APP.toRegex().matches(value)) {
            appName = value.trim()
        } else {
            showErrorMessage("ERROR: Invalid App Name: $value (needs to follow standard pattern {MyProject} or {My Project})")
        }
    }

    private fun validatePackageName(value: String) {
        if (PATTERN_PACKAGE.toRegex().matches(value)) {
            packageName = value.trim()
        } else {
            showErrorMessage("ERROR: Invalid Package Name: $value (needs to follow standard pattern {com.example.package})")
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
            .walk()
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
            .walk()
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
        executeCommand("sh $projectPath${fileSeparator}gradlew -p $fileSeparator$projectPath assembleDebug")
        showMessage("=> 🚓 Running tests...")
        executeCommand("sh $projectPath${fileSeparator}gradlew -p $fileSeparator$projectPath testStagingDebugUnitTest")
        showMessage("=> 🚀 Done! The project is ready for development")
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
            showErrorMessage("❌ Something went wrong! when executing command: $command", exitValue)
        }
    }

    private fun renameAppName() {
        showMessage("=> 🔎 Renaming app name...")
        File(projectPath)
            .walk()
            .filter { it.name == "strings.xml" }
            .forEach { filePath ->
                rename(
                    sourcePath = filePath.toString(),
                    oldValue = TEMPLATE_APP_NAME,
                    newValue = appName
                )
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

    private fun showErrorMessage(message: String, exitCode: Int = 0) {
        println("\n${message}\n")
        System.exit(exitCode)
    }

    private fun String.uppercaseEveryFirstCharacter(): String {
        return this.split(SPACE_SEPARATOR).joinToString(separator = SPACE_SEPARATOR) { string ->
            string.replaceFirstChar { it.uppercase() }
        }
    }

    private fun String.getStringWithoutSpace(): String {
        return this.replace(SPACE_SEPARATOR, "")
    }
}

NewProject.generate(args)
