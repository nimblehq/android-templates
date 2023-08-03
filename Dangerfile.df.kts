import systems.danger.kotlin.*

danger(args) {
    onGitHub {
        // Make it more obvious that a PR is a work in progress and shouldn't be merged yet
        if (pullRequest.title.contains("WIP", false)) {
            warn("PR is classed as Work in Progress")
        }

        // Warn when there is a big PR
        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 500) {
            warn("Big PR")
        }

        // Warn to encourage a PR description
        if (pullRequest.body?.isBlank() == true) {
            warn("Please provide a summary in the PR description to make it easier to review")
        }
    }
}
