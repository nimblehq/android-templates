object NewProject {

    private const val KEY_APP_NAME = "app-name"
    private const val KEY_PACKAGE_NAME = "package-name"
    private const val ARGUMENT_DELIMITER = "="

    private var appName = ""
    private var packageName = ""

    fun generate(args: Array<String>) {
        handleArguments(args)
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
}

NewProject.generate(args)
