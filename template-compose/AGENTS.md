# AGENTS.md

You are an experienced Android app developer. Follow official Android architecture recommendations.

Android app template using [Nimble Compass](https://nimblehq.co/compass/) conventions.

**Stack:** Kotlin, Jetpack Compose, Hilt, Coroutines, Retrofit + Moshi
**Min SDK:** 24 | **Target SDK:** 34 | **JVM:** Java 17

## Architecture

Follow layered architecture with unidirectional data flow (UDF):

```
app/      → UI, navigation, DI wiring
data/     → API, storage, repository implementations
domain/   → Business logic, models, repository interfaces (no Android deps)
```

Dependency flow: `app` → `domain` ← `data`

Place all business logic in ViewModels. Use lifecycle-aware UI state collection.

## Key Files

- Main activity: `app/src/main/java/co/nimblehq/template/compose/MainActivity.kt`
- Navigation: `app/src/main/java/co/nimblehq/template/compose/ui/AppNavigation.kt`
- Base ViewModel: `app/src/main/java/co/nimblehq/template/compose/ui/base/BaseViewModel.kt`
- API service: `data/src/main/java/co/nimblehq/template/compose/data/remote/services/`
- Build config: `build.gradle.kts` (root) and module-level build files

## Commands

```bash
# Build
./gradlew assembleDebug

# Static analysis
./gradlew detekt

# Lint
./gradlew lint

# Tests
./gradlew app:testStagingDebugUnitTest data:testDebugUnitTest domain:test

# Coverage
./gradlew koverXmlReportCustom
```

## Testing

Before commit: `./gradlew detekt lint`
Before PR: `./gradlew detekt lint app:testStagingDebugUnitTest data:testDebugUnitTest domain:test koverXmlReportCustom`

**Coverage:** 80% project, 95% per-file
**Libraries:** MockK, Kotest, Turbine, Robolectric

Test naming:
```kotlin
@Test
fun `When user taps login, it shows loading`() { }
```

## Code Style

### Kotlin
- 4-space indent, 120 char line limit
- Max 150 lines/class, 60 lines/method, 5 params
- Boolean naming: `is`/`has`/`can` prefix

### Compose

All new UI components must be built with Jetpack Compose. Don't suggest XML-based layouts.

Composables use PascalCase. Modifier is first optional param.

```kotlin
@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) { }
```

Screen pattern — public wrapper with ViewModel, private stateless content:
```kotlin
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel(),
    navigator: (BaseDestination) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FeatureScreenContent(state = state)
}

@Composable
private fun FeatureScreenContent(
    state: FeatureUiState,
    modifier: Modifier = Modifier,
) { }
```

## Code Organization

- **Domain models** → `domain/src/main/java/.../domain/models/`
- **Repository interfaces** → `domain/src/main/java/.../domain/repositories/`
- **UseCases** → `domain/src/main/java/.../domain/usecases/`
- **API models** → `data/src/main/java/.../data/remote/models/`
- **Repository impls** → `data/src/main/java/.../data/repositories/`
- **Screens/ViewModels** → `app/src/main/java/.../ui/screens/{feature}/`
- **DI modules** → `app/src/main/java/.../di/modules/`
- **Tests** → Mirror source path in `src/test/`

## Git Workflow

Branches: `main` (prod), `develop` (staging), feature branches from `develop`

Commit: `[#123] Add feature` — capital letter, present tense, one change per commit

PR: `[#ticket] Description` — 2 approvals, <500 lines, add labels

## Security

- API keys in `.properties` files (gitignored)
- Sensitive data → `EncryptedSharedPreferences`
- Never log tokens, passwords, or PII
- HTTPS only, WebView JS disabled, `exported="false"` default

## Detekt Limits

120 chars/line, 150 lines/class, 60 lines/method, 5 params, 3 nested blocks, no magic numbers (except -1,0,1,2)

## Boundaries

✅ **Required:**
- Follow app/data/domain layered architecture
- Use Hilt for DI, StateFlow for UI state
- Extend `BaseViewModel` for all ViewModels
- Write tests for new code
- Use Jetpack Compose for all UI (no XML layouts)

⚠️ **Ask before:**
- Adding dependencies or modules
- Changing Detekt/ProGuard/CI config

🚫 **Don't:**
- Commit secrets
- Push to `main`/`develop` directly
- Skip tests
- Use LiveData (use StateFlow)
- Create ViewModels directly (use `hiltViewModel()`)
- Pass ViewModels to child composables
- Suggest XML-based layouts
