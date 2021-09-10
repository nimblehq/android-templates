@file:DependsOn("io.github.ackeecz:danger-kotlin-commit-lint:0.1.0")
@file:DependsOn("io.github.ackeecz:danger-kotlin-detekt:0.1.4")

import systems.danger.kotlin.*

import io.github.ackeecz.danger.commitlint.*
import io.github.ackeecz.danger.detekt.DetektPlugin

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.BiPredicate
import java.util.stream.Collectors

register plugin CommitLintPlugin
register plugin DetektPlugin

danger(args) {

    // GitHub checks 
    val allSourceFiles = git.modifiedFiles + git.createdFiles
    val changelogChanged = allSourceFiles.contains("CHANGELOG.md")
    val sourceChanges = allSourceFiles.firstOrNull { it.contains("src") }

    onGitHub {
        val isTrivial = pullRequest.title.contains("#trivial")

        // Changelog update check
        if (!isTrivial && !changelogChanged && sourceChanges != null) {
            warn("Any changes to library code should be reflected in the Changelog.\n\nPlease consider adding a note there and adhere to the [Changelog Guidelines](https://github.com/Moya/contributors/blob/master/Changelog%20Guidelines.md).")
        }

        // Big PR check
        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 500) {
            warn("Big PR, try to keep changes smaller if you can")
        }

        // Work in progress check
        if (pullRequest.title.contains("WIP", false)) {
            warn("PR is classed as Work in Progress")
        }
    }

    // Commit messages align with recommendations specified check
    CommitLintPlugin.check(commits = git.commits.map { gitCommit ->
        Commit(CommitMessage.fromRawMessage(gitCommit.message), gitCommit.sha ?: "")
    })

    // Detekt output check
    val detektReports = Files.find(Paths.get(""), 10, BiPredicate { path, _ ->
        val fileName = path.toFile().name
        fileName.endsWith("detekt.xml")
    }).map { it.toFile() }.collect(Collectors.toList())
    DetektPlugin.parseAndReport(*detektReports.toTypedArray())
}