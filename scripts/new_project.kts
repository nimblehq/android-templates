import java.io.File

object NewProject {

    private const val ARGUMENT_DELIMITER = "="
    private const val DOT_SEPARATOR = "."
    private const val KEY_APP_NAME = "app-name"
    private const val KEY_HELP = "--help"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val MINUS_SEPARATOR = "-"
    private const val PATTERN_APP = "^([A-Z][a-zA-Z0-9\\s]*)|([a-z][a-z0-9-]*)$"
    private const val PATTERN_PACKAGE = "^[a-z]+(\\.[a-z][a-z0-9]*)+$"
    private const val SPACE_SEPARATOR = " "
    private const val TEMPLATE_APP_NAME = "Template"
    private const val TEMPLATE_APPLICATION_CLASS_NAME = "TemplateApplication"
    private const val TEMPLATE_FOLDER_NAME = "template"
    private const val TEMPLATE_PACKAGE_NAME = "co.nimblehq.template"

    private val helpMessage = """
        Run kscript new_project.kts to create a new project with the following arguments:
            package-name=   New package name (i.e., com.example.package)
            app-name=       New app name (i.e., MyApp, "My App", "my-app")
        
        Example: kscript new_project.kts package-name=co.myproject.example app-name="My Project"
    """.trimIndent()

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
        var hasAppName = false
        var hasPackageName = false
        args.forEach { arg ->
            when {
                arg == KEY_HELP -> {
                    showMessage(
                        message = helpMessage,
                        exitAfterMessage = true
                    )
                }
                arg.startsWith("$KEY_APP_NAME$ARGUMENT_DELIMITER") -> {
                    val (key, value) = arg.split(ARGUMENT_DELIMITER)
                    validateAppName(value)
                    hasAppName = true
                }
                arg.startsWith("$KEY_PACKAGE_NAME$ARGUMENT_DELIMITER") -> {
                    val (key, value) = arg.split(ARGUMENT_DELIMITER)
                    validatePackageName(value)
                    hasPackageName = true
                }
                else -> {
                    showMessage(
                        message = "ERROR: Invalid argument name: $arg \n$helpMessage",
                        exitAfterMessage = true
                    )
                }
            }
        }
        when {
            !hasAppName -> showMessage(
                message = "ERROR: No app name has been provided \n$helpMessage",
                exitAfterMessage = true
            )
            !hasPackageName -> showMessage(
                message = "ERROR: No package name has been provided \n$helpMessage",
                exitAfterMessage = true
            )
        }
    }

    private fun validateAppName(value: String) {
        if (PATTERN_APP.toRegex().matches(value)) {
            appName = value.trim()
        } else {
            showMessage(
                message = "ERROR: Invalid App Name: $value (needs to follow standard pattern {MyProject} or {My Project}) or {my-project} \n$helpMessage",
                exitAfterMessage = true
            )
        }
    }

    private fun validatePackageName(value: String) {
        if (PATTERN_PACKAGE.toRegex().matches(value)) {
            packageName = value.trim()
        } else {
            showMessage(
                message = "ERROR: Invalid Package Name: $value (needs to follow standard pattern {com.example.package}) \n$helpMessage",
                exitAfterMessage = true
            )
        }
    }

    private fun initializeNewProjectFolder() {
        showMessage("=> 🐢 Initializing new project...")
        copyFiles(fromPath = rootPath + TEMPLATE_FOLDER_NAME, toPath = projectPath)
        // Set gradlew file as executable, because copying files from one folder to another doesn't copy file permissions correctly (= read, write & execute).
        File(projectPath + fileSeparator + "gradlew")?.setExecutable(true)
    }

    private fun cleanNewProjectFolder() {
        executeCommand(
            command = arrayOf(
                "sh",
                "$projectPath${fileSeparator}gradlew",
                "-p",
                "$projectPath",
                "clean"
            )
        )
        executeCommand(
            command = arrayOf(
                "sh",
                "$projectPath${fileSeparator}gradlew",
                "-p",
                "$projectPath${fileSeparator}buildSrc",
                "clean"
            )
        )
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
        executeCommand(
            command = arrayOf(
                "sh",
                "$projectPath${fileSeparator}gradlew",
                "-p",
                "$projectPath",
                "assembleDebug"
            )
        )
        showMessage("=> 🚓 Running tests...")
        executeCommand(
            command = arrayOf(
                "sh",
                "$projectPath${fileSeparator}gradlew",
                "-p",
                "$projectPath",
                "testStagingDebugUnitTest"
            )
        )
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

    /**
     * Execute a shell command
     *
     * Runtime.getRuntime().exec(String) will partition a command automatically, based on white spaces.
     * If a file path contains a white space, it will not be able to find the file and result in an error.
     *
     * -> Example: "Desktop/My Projects/android-templates" -> ["Desktop/My", "Projects/android-templates"]
     * -> Reference: https://stackoverflow.com/questions/33077129/java-runtime-exec-with-white-spaces-on-path-name
     *
     * @param [command]: A command partioned in to an array of strings
     */
    private fun executeCommand(command: Array<String>) {
        val process = Runtime.getRuntime().exec(command)
        process.inputStream.reader().forEachLine { println(it) }
        process.errorStream.reader().forEachLine { println(it) }
        val exitValue = process.waitFor()
        if (exitValue != 0) {
            showMessage(
                message = "❌ Something went wrong! when executing command: $command",
                exitAfterMessage = true,
                exitValue = exitValue
            )
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

    private fun showMessage(
        message: String,
        exitAfterMessage: Boolean = false,
        exitValue: Int = 0
    ) {
        println("\n${message}\n")
        if (exitAfterMessage) System.exit(exitValue)
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
