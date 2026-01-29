# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classified as Work in Progress") if github.pr_title.include? "WIP"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Warn to encourage a PR description
warn("Please provide a summary in the PR description to make it easier to review") if github.pr_body.length == 0

# Warn to encourage that labels should have been used on the PR
warn("Please add labels to this PR") if github.pr_labels.empty?

# Check commits lint and warn on all checks (instead of failing)
commit_lint.check warn: :all, disable: [:subject_length]

# Detekt output check
detekt_dir = "**/build/reports/detekt/detekt.xml"
detekt_reports = Dir[detekt_dir]

if detekt_reports.any?
  detekt_reports.each do |file_name|
    kotlin_detekt.skip_gradle_task = true
    kotlin_detekt.report_file = file_name
    kotlin_detekt.detekt(inline_mode: true)
  end
else
  warn("Detekt report not found. Please run `./gradlew detekt` before creating a PR.")
end

# Android Lint output check
lint_dir = "**/**/build/reports/lint/lint-result.xml"
lint_reports = Dir[lint_dir]

if lint_reports.any?
  lint_reports.each do |file_name|
    android_lint.skip_gradle_task = true
    android_lint.report_file = file_name
    android_lint.lint(inline_mode: true)
  end
else
  warn("Android Lint report not found. Please run `./gradlew lint` before creating a PR.")
end

# Show Danger test coverage report from Kover for templates
# Report coverage of modified files, warn if total project coverage is under 80%
# or if any modified file's coverage is under 95%
kover_file_template_compose = "template-compose/app/build/reports/kover/reportCustom.xml"

if File.exist?(kover_file_template_compose)
  markdown "## Kover report for template-compose:"
  shroud.reportKover moduleName: "Template - Compose Unit Tests",
                     file: kover_file_template_compose,
                     totalProjectThreshold: 80,
                     modifiedFileThreshold: 95,
                     failIfUnderProjectThreshold: false,
                     failIfUnderFileThreshold: false
else
  warn("Kover coverage report not found at #{kover_file_template_compose}. Please run `./gradlew koverXmlReportCustom` before creating a PR.")
end
