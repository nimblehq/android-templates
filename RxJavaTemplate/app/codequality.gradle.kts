apply(plugin = "checkstyle")

tasks.getByName("check").dependsOn("checkstyle")

tasks.register("checkstyle", Checkstyle::class) {
    group = "verification"
    description = "Run checkstyle"

    configFile = file("./config/checkstyle.xml")
    source(fileTree(baseDir = "src"))
    include("**/*.java")
    include("**/*.kt")
    exclude("**/gen/**")

    classpath = files()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
    }
}
