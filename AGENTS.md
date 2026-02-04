# AGENTS.md

You are an experienced Android developer working on a project template generator.

Android project template generator using [Nimble Compass](https://nimblehq.co/compass/) conventions.

**Stack:** Kotlin, Jetpack Compose, Hilt, Kscript (generator)

## Project Structure

- Generator script: `scripts/new_project.kts` (Kscript)
- Source template: `template-compose/` (see its AGENTS.md for Android-specific guidance)
- Example output: `sample-compose/` (regenerate, don't edit directly)

## Commands

```bash
# Build template
cd template-compose && ./gradlew assembleDebug

# Static analysis
cd template-compose && ./gradlew detekt lint

# Run tests
cd template-compose && ./gradlew app:testStagingDebugUnitTest data:testDebugUnitTest domain:test

# Coverage report
cd template-compose && ./gradlew koverXmlReportCustom

# Generate new project
cd scripts && kscript new_project.kts package-name=com.example.app app-name="My App" template=compose
```

## Testing

Before commit:
```bash
cd template-compose && ./gradlew detekt lint assembleDebug
```

Before PR:
```bash
cd template-compose && ./gradlew detekt lint app:testStagingDebugUnitTest data:testDebugUnitTest domain:test koverXmlReportCustom
```

After changing template, verify generator works:
```bash
cd scripts && kscript new_project.kts package-name=co.test.app app-name="Test App" template=compose
```

CI pipeline: Detekt → Lint → Tests → Coverage → Danger

## Template Placeholders

The generator replaces these strings — don't modify them:

| Placeholder | Replaced With |
|-------------|---------------|
| `co.nimblehq.template.compose` | Package name |
| `co/nimblehq/template/compose` | Package path |
| `Template Compose` | App name |
| `TemplateCompose` | App name (no spaces) |

## Git Workflow

- `main` — Production. Never commit directly.
- `develop` — Staging. Never commit directly.
- Feature branches from `develop`

Commit format: `[#123] Add feature` or `[Brand-456] Fix bug`

PR title: `[#ticket] Description`

## Boundaries

✅ **Required:**
- Run `detekt lint` before commits
- Test generator after template changes
- 2 PR approvals

⚠️ **Ask before:**
- Adding modules to template
- Changing generator behavior
- Changing Detekt/coverage thresholds
- Adding CI workflows

🚫 **Don't:**
- Edit `sample-compose/` directly (regenerate it)
- Push to `main` or `develop`
- Skip CI checks
- Commit secrets or credentials
