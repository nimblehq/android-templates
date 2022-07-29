<p align="center">
  <a href="https://nimblehq.co/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://assets.nimblehq.co/logo/dark/logo-dark-text-320.png">
      <img alt="Nimble logo" src="https://assets.nimblehq.co/logo/light/logo-light-text-320.png">
    </picture>    
  </a>
</p>

---

Our Android template: **[template](https://github.com/nimblehq/android-templates/tree/develop/template)**

## Setup

1. Clone or download this repository to your local machine, then extract and open the folder
2. Install [Kscript](https://github.com/holgerbrandl/kscript#installation)
3. Run `kscript new_project.kts` to create a new project with the following arguments:
  ```   
    package-name=                      New package name (i.e. com.example.package)
    app-name=                          New app name (i.e. MyApp, "My App")
  ```

  Example: `kscript new_project.kts package-name=co.myproject.example app-name="My Project"`

4. Update `android_version_code` and `android_version_name` in `template/build.gradle`

## About

<a href="https://nimblehq.co/">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://assets.nimblehq.co/logo/dark/logo-dark-text-160.png">
    <img alt="Nimble logo" src="https://assets.nimblehq.co/logo/light/logo-light-text-160.png">
  </picture>    
</a>

This project is maintained and funded by Nimble.

Learn more about our Android templates on the [Wiki](https://github.com/nimblehq/android-templates/wiki).
