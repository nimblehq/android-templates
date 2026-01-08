# Dependabot Configuration Guide

## Overview

This repository uses [Dependabot](https://docs.github.com/en/code-security/dependabot) to automatically keep dependencies up-to-date. Dependabot monitors our Gradle dependencies and GitHub Actions, creating pull requests when updates are available.

## What's Configured

### Package Ecosystems

| Ecosystem | Scope | Update Frequency | Open PR Limit |
|-----------|-------|------------------|---------------|
| **Gradle** | template-compose, sample-compose | Weekly (Mondays, 09:00 UTC) | 5 per directory |
| **GitHub Actions** | All workflows in `.github/workflows/` | Weekly (Mondays, 09:00 UTC) | 3 |

### Dependency Groups

To reduce PR noise, dependencies are grouped by category:

#### Gradle Dependencies

- **androidx**: All AndroidX libraries (excluding Compose)
- **compose**: Compose BOM and all Compose libraries
- **kotlin**: Kotlin compiler, coroutines, and KSP
- **networking**: Retrofit, OkHttp, Moshi
- **dependency-injection**: Hilt and Dagger
- **testing**: JUnit, Mockk, Kotest, Turbine, Robolectric
- **build-tools**: Gradle plugins, Detekt, Kover

## How It Works

### Automatic Updates

1. **Every Monday at 09:00 UTC**, Dependabot checks for dependency updates
2. When updates are found, Dependabot creates **grouped PRs** based on the configuration
3. Each PR includes:
   - Updated version in `gradle/libs.versions.toml` (for Gradle)
   - Updated version in workflow files (for GitHub Actions)
   - Changelog information from the dependency
   - Compatibility score

### PR Labels

Dependabot PRs are automatically labeled:

- `dependencies`: All Dependabot PRs
- `gradle`: Gradle dependency updates
- `template-compose`: Updates for template-compose
- `sample-compose`: Updates for sample-compose
- `github-actions`: GitHub Actions updates
- `ci-cd`: CI/CD workflow updates

### Reviewers

PRs are automatically assigned to: **@nimblehq/android-team**

## Reviewing Dependabot PRs

### Quick Review Checklist

- [ ] Check CI/CD status - all tests must pass
- [ ] Review changelog for breaking changes
- [ ] For major version updates, check migration guide
- [ ] Verify compatibility with current Android minSdk (24)
- [ ] Test locally if the update affects core functionality

### Update Types

| Type | Example | Review Effort | Risk |
|------|---------|---------------|------|
| **Patch** | 1.2.3 → 1.2.4 | Low - Bug fixes only | Low |
| **Minor** | 1.2.0 → 1.3.0 | Medium - New features | Medium |
| **Major** | 1.x.x → 2.0.0 | High - Breaking changes | High |

### Common Scenarios

#### ✅ Safe to Merge (After CI Passes)

- Patch version updates
- Minor updates for well-maintained libraries
- Test dependency updates
- GitHub Actions minor updates

#### ⚠️ Requires Manual Testing

- Major version updates
- Compose BOM updates (may affect UI)
- Kotlin compiler updates
- Gradle plugin updates
- Breaking changes noted in changelog

#### 🔍 Deep Review Required

- AndroidX major updates
- Hilt/Dagger updates (affects DI)
- Retrofit/OkHttp updates (affects networking)
- Security-related updates

## Managing Dependabot

### Ignoring Specific Updates

To ignore a specific dependency version, comment on the Dependabot PR:

```
@dependabot ignore this major version
@dependabot ignore this minor version
@dependabot ignore this dependency
```

Or add to `.github/dependabot.yml`:

```yaml
ignore:
  - dependency-name: "androidx.compose:compose-bom"
    versions: ["2024.01.00"]
```

### Rebasing Dependabot PRs

If a Dependabot PR has conflicts:

```
@dependabot rebase
```

### Recreating Dependabot PRs

If you closed a PR but want to recreate it:

```
@dependabot recreate
```

## Configuration Details

### File Location

`.github/dependabot.yml`

### Key Settings

- **Version**: 2 (current Dependabot version)
- **Schedule**: Weekly on Mondays at 09:00 UTC
- **Commit Message Prefix**: `[Chore]` to match team conventions
- **Auto-rebase**: Enabled by default

### Gradle Version Catalog Support

Dependabot has **native support** for Gradle Version Catalogs (`libs.versions.toml`). Updates are made directly to the TOML file, and all modules referencing those versions are automatically updated.

**Example Update:**

```toml
# Before
[versions]
kotlin = "1.9.0"

# After (Dependabot PR)
[versions]
kotlin = "1.9.10"
```

All modules using `alias(libs.plugins.kotlin.android)` are automatically updated.

## Troubleshooting

### Dependabot Not Creating PRs

**Possible Causes:**

1. All dependencies are up-to-date
2. Open PR limit reached (5 for Gradle, 3 for GitHub Actions)
3. Dependabot is disabled at the organization level
4. Configuration file has syntax errors

**Solutions:**

1. Check Dependabot insights: `https://github.com/nimblehq/android-templates/network/updates`
2. Validate YAML syntax: [YAML Lint](http://www.yamllint.com/)
3. Check organization settings for Dependabot enablement

### Too Many PRs

If Dependabot creates too many PRs:

1. **Adjust grouping** - Add more dependencies to existing groups
2. **Reduce frequency** - Change from `weekly` to `monthly`
3. **Lower PR limits** - Reduce `open-pull-requests-limit`

### Breaking Changes

If a Dependabot PR introduces breaking changes:

1. Close the PR with a comment explaining the issue
2. Create a manual PR with necessary migration changes
3. Add ignore rule if the breaking change can't be resolved immediately

## Best Practices

### ✅ Do

- Review and merge Dependabot PRs regularly
- Run full test suite before merging major updates
- Check changelogs for breaking changes
- Keep dependencies up-to-date for security
- Use `@dependabot` commands to manage PRs

### ❌ Don't

- Ignore Dependabot PRs for extended periods
- Merge without CI/CD passing
- Skip review on major version updates
- Disable Dependabot without team discussion
- Manually edit Dependabot PRs (use rebase instead)

## Resources

- [Dependabot Documentation](https://docs.github.com/en/code-security/dependabot)
- [Dependabot Configuration Options](https://docs.github.com/en/code-security/dependabot/dependabot-version-updates/configuration-options-for-the-dependabot.yml-file)
- [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)
- [Managing Dependabot PRs](https://docs.github.com/en/code-security/dependabot/working-with-dependabot/managing-pull-requests-for-dependency-updates)
- [How to use Dependabot with Gradle’s Version Catalog](https://medium.com/@vh.dev/dependabot-in-action-d9b56b2be86c)

**Last Updated:** January 2026
**Configuration Version:** 2
**Maintained By:** @nimblehq/android-team
