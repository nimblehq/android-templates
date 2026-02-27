# AGENTS.md

You are an experienced Android developer working on a project template generator.

Android project template generator using [Nimble Compass](https://nimblehq.co/compass/) conventions.

## Technology Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM / Clean Architecture (app → domain ← data) |
| **Dependency Injection** | Hilt |
| **Generator** | Kscript |
| **Build System** | Gradle (Kotlin DSL) |
| **Static Analysis** | Detekt, Android Lint |
| **Coverage** | Kover |

## Project Structure

```
/scripts          → Generator script (new_project.kts)
/template-compose → Source template (see its AGENTS.md for Android guidance)
/sample-compose   → Example output (regenerate, don't edit directly)
/build-logic      → Shared Gradle convention plugins
/.github          → CI workflows and PR templates
```

## Prerequisites

- Android Studio (Latest Stable)
- JDK 17+
- Kscript installed (`brew install holgerbrandl/tap/kscript`)

## Commands

### Build
```bash
cd template-compose && ./gradlew assembleDebug
```

### Static Analysis
```bash
cd template-compose && ./gradlew detekt lint
```

### Tests
```bash
cd template-compose && ./gradlew app:testStagingDebugUnitTest data:testDebugUnitTest domain:test
```

### Coverage
```bash
cd template-compose && ./gradlew koverXmlReportCustom
```

### Generate New Project
```bash
cd scripts && kscript new_project.kts package-name=com.example.app app-name="My App" template=compose
```

## Testing

**Before commit:**
```bash
cd template-compose && ./gradlew detekt lint assembleDebug
```

**Before PR:**
```bash
cd template-compose && ./gradlew detekt lint app:testStagingDebugUnitTest data:testDebugUnitTest domain:test koverXmlReportCustom
```

**After template changes:** Verify generator works:
```bash
cd scripts && kscript new_project.kts package-name=co.test.app app-name="Test App" template=compose
```

## Configuration Files

| File | Purpose |
|------|---------|
| `template-compose/config/detekt/detekt.yml` | Detekt rules |
| `.github/workflows/run_detekt_and_unit_tests.yml` | CI pipeline (Detekt + unit tests) |
| `.github/workflows/review_pull_request.yml` | PR automation and checks |
| `.github/workflows/verify_newproject_script.yml` | Validate generator script on PRs |

## CI/CD

**Pipeline:** Detekt → Lint → Tests → Coverage → Danger

Workflows defined in `.github/workflows/`:
- `run_detekt_and_unit_tests.yml` — Main CI (Detekt + unit tests)
- `review_pull_request.yml` — PR automation and checks
- `verify_newproject_script.yml` — Validate generator script on PRs

## Template Placeholders

The generator replaces these strings — don't modify them:

| Placeholder | Replaced With |
|-------------|---------------|
| `co.nimblehq.template.compose` | Package name |
| `co/nimblehq/template/compose` | Package path |
| `Template Compose` | App name |
| `TemplateCompose` | App name (no spaces) |

## Git Workflow

**Branches:**
- `main` — Production (protected, no direct commits)
- `develop` — Staging (protected, no direct commits)
- Feature branches from `develop` using `kebab-case`: `feature/add-login`, `bug/fix-crash`

**Commits:**
- Format: `[#123] Add feature` — capitalize first word, present tense
- One logical change per commit
- Include ticket ID for traceability

**Pull Requests:**
- Title: `[#ticket] Description`
- 2 approvals required (include Team Lead or senior for large squads)
- Target merge within 2-3 days
- Only Team Lead merges to protected branches

## Key Guidelines for Agents

1. **Template Awareness**: Never modify placeholder strings used by the generator
2. **Regenerate, Don't Edit**: Changes to `sample-compose/` are overwritten — modify `template-compose/` instead
3. **Static Analysis**: Always run `./gradlew detekt lint` before committing
4. **Test Generator**: After any template change, verify generation still works
5. **Module Boundaries**: Core modules must not depend on feature modules
6. **Compose Only**: All UI must use Jetpack Compose — never suggest XML layouts

## Boundaries

✅ **Required:**
- Run `detekt lint` before commits
- Test generator after template changes
- 2 PR approvals
- Follow Nimble Compass conventions

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
- Merge PRs without Team Lead approval
