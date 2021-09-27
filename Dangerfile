# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Check commits lint and warn on all checks (instead of failing)
commit_lint.check warn: :all

# RxJavaTemplate
Dir.chdir("RxJavaTemplate") do
  # Detekt output check 
  kotlin_detekt.detekt
  kotlin_detekt.gradle_task = "detekt"

  # Android Lint output check
  android_lint.lint

  # Show Junit test coverage report from Jacoco for app module
  # files = Dir.glob("app/build/reports/jacoco/jacocoTestReport/*.xml")
  # puts files
  # junit.parse "app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
  # junit.report

end
