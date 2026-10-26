# AGENTS.md

You are an experienced Android app developer. Follow official Android architecture recommendations and [Nimble Compass](https://nimblehq.co/compass/) conventions.

## Project Overview

Android app template implementing modern Android development best practices. This template serves as the foundation for Nimble's Android projects, demonstrating Clean Architecture with Jetpack Compose.

## Technology Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose with Material 3 |
| **Architecture** | MVVM / Clean Architecture |
| **State Management** | Unidirectional Data Flow (UDF) with StateFlow |
| **Dependency Injection** | Hilt |
| **Navigation** | Navigation 3 (`androidx.navigation3`) |
| **Async** | Kotlin Coroutines + Flow |
| **Networking** | Retrofit + OkHttp + Moshi |
| **Local Storage** | DataStore, Room (when needed) |
| **Build System** | Gradle (Kotlin DSL) |
| **Min SDK** | 24 |
| **Target SDK** | 34 |
| **JVM** | Java 17 |

## Architecture

This project follows Google's official architecture guidance with a layered, modular structure:

- **UI Layer (`app/`):** Built entirely with Jetpack Compose. ViewModels act as state holders, exposing UI state as StateFlow streams.
- **Domain Layer (`domain/`):** Pure Kotlin module with business logic, models, and repository interfaces. No Android dependencies.
- **Data Layer (`data/`):** Repository implementations, API clients, and local storage. Depends on domain.

**Dependency Flow:** `app` → `domain` ← `data`

```
┌─────────────────────────────────────────────────────┐
│  app/                                               │
│  ├── common/      → BaseViewModel, BaseEvent,      │
│  │                  BaseIntent, BaseViewState,     │
│  │                  ui/BaseScreen                  │
│  ├── di/          → Hilt modules                   │
│  ├── extensions/  → Kotlin extensions              │
│  ├── navigation/  → AppNavigation.kt (NavDisplay), │
│  │                  entry/ (routes + entries),     │
│  │                  navigator/ (AppNavigator)      │
│  ├── ui/                                            │
│  │   ├── screens/ → Feature screens & ViewModels   │
│  │   └── theme/   → AppColors, AppTypography, etc. │
│  ├── util/        → DeepLinkPattern, DeepLinkRequest,│
│  │                  DeepLinkMatcher, KeyDecoder    │
│  └── MainApplication.kt                            │
├─────────────────────────────────────────────────────┤
│  domain/                                            │
│  ├── exceptions/  → Custom exceptions              │
│  ├── models/      → Domain entities                │
│  ├── repositories/→ Repository interfaces          │
│  └── usecases/    → Business logic                 │
├─────────────────────────────────────────────────────┤
│  data/                                              │
│  ├── local/       → DataStore, Room                │
│  ├── remote/      → API services, models, clients  │
│  └── repositories/→ Repository implementations     │
└─────────────────────────────────────────────────────┘
```

## Prerequisites

- Android Studio (Latest Stable or Preview for new features)
- JDK 17+
- API keys configured in `local.properties` (see README.md)

## Build Variants

The app has two product flavors and three build types:

| Flavor | Purpose |
|--------|---------|
| `staging` | Development/QA environment |
| `production` | Production environment |

| Build Type | Purpose |
|------------|---------|
| `debug` | Development with debug tools |
| `preRelease` | Release-like build with debug certificate |
| `release` | Optimized, ProGuard-enabled |

**Default development:** `assembleStagingDebug`

## Commands

### Build
```bash
./gradlew assembleStagingDebug
```

### Static Analysis
```bash
./gradlew detekt lint
```

### Tests
```bash
# All unit tests
./gradlew app:testStagingDebugUnitTest data:testDebugUnitTest domain:test

# Single test class
./gradlew app:testStagingDebugUnitTest --tests "co.nimblehq.template.compose.ui.screens.home.HomeViewModelTest"
```

### Coverage
```bash
./gradlew koverXmlReportCustom
```

## Testing

**Before commit:** `./gradlew detekt lint`

**Before PR:** `./gradlew detekt lint app:testStagingDebugUnitTest data:testDebugUnitTest domain:test koverXmlReportCustom`

### Test Frameworks

| Purpose | Library |
|---------|---------|
| **Mocking** | MockK |
| **Assertions** | Kotest matchers |
| **Flow testing** | Turbine |
| **Android testing** | Robolectric |
| **UI testing** | ComposeTestRule |

### Coverage Requirements
- **Project:** 80% minimum
- **Per-file:** 95% minimum

### Test Naming Convention
```kotlin
@Test
fun `When user taps login, it shows loading`() { }
```

## Key Files

| File | Purpose |
|------|---------|
| `app/.../MainActivity.kt` | Single activity entry point, deep link handling |
| `app/.../navigation/AppNavigation.kt` | NavDisplay setup (transitions, decorators) |
| `app/.../navigation/navigator/AppNavigator.kt` | Back stack navigator |
| `app/.../common/BaseViewModel.kt` | ViewModel base class |
| `data/.../remote/services/` | API service definitions |
| `detekt-config.yml` | Detekt rules |

## Configuration Files

| File | Purpose |
|------|---------|
| `detekt-config.yml` | Static analysis rules |
| `app/proguard-rules.pro` | ProGuard configuration |
| `local.properties` | API keys (gitignored) |

## Code Style

### Kotlin (Nimble Compass)
- **Indent:** 4 spaces (no tabs)
- **Line length:** 120 characters max
- **Class size:** 150 lines max
- **Method size:** 60 lines max
- **Parameters:** 5 max
- **Nested blocks:** 3 max
- **Boolean naming:** `is`/`has`/`can` prefix (e.g., `isValid`, `hasError`)
- **Trailing commas:** Enabled for cleaner diffs
- **Method chaining:** Each call on separate line, break before dot

### Compose

Composables use PascalCase. Modifier is always the first optional parameter.

```kotlin
@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) { }
```

**Screen pattern** — public wrapper with ViewModel, private stateless content. Navigation is MVI:
the ViewModel emits a `NavigationEvent`/navigate-back event via `BaseViewModel`, and the screen
collects it and forwards it to an `onNavigate`/`onNavigateBack` callback supplied by its
`EntryProviderInstaller` (see [Navigation](#navigation) below):

```kotlin
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel(),
    onNavigate: (NavKey) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is NavigationEvent) onNavigate(event.destination)
        }
    }
    FeatureScreenContent(state = state, onAction = viewModel::onAction)
}

@Composable
private fun FeatureScreenContent(
    state: FeatureUiState,
    onAction: (FeatureAction) -> Unit,
    modifier: Modifier = Modifier,
) { }
```

### Navigation

Routes and their entries live in `navigation/entry/`, one file per screen:

```kotlin
@Serializable
data object FeatureRoute : NavKey

fun EntryProviderScope<Any>.featureEntry(onNavigate: (NavKey) -> Unit) {
    entry<FeatureRoute> {
        FeatureScreen(viewModel = hiltViewModel(), onNavigate = onNavigate)
    }
}
```

Register the entry with its own `@IntoSet EntryProviderInstaller` Hilt binding in
`di/modules/NavigationModule.kt` — **one provider per feature**, not one combined provider for
multiple screens:

```kotlin
@IntoSet
@Provides
fun provideFeatureEntryProviderInstaller(navigator: AppNavigator): EntryProviderInstaller = {
    featureEntry(onNavigate = navigator::goTo)
}
```

To make a route reachable via deep link, register a `DeepLinkPattern` for it in
`MainActivity.deepLinkPatterns` (and add a matching `<intent-filter>` in `AndroidManifest.xml` if
it should be reachable from outside the app) — see the KDoc on `util/DeepLinkPattern.kt`.

## Code Organization

| Type | Location |
|------|----------|
| Domain models | `domain/src/main/java/.../domain/models/` |
| Repository interfaces | `domain/src/main/java/.../domain/repositories/` |
| UseCases | `domain/src/main/java/.../domain/usecases/` |
| API models | `data/src/main/java/.../data/remote/models/` |
| Repository impls | `data/src/main/java/.../data/repositories/` |
| Screens/ViewModels | `app/src/main/java/.../ui/screens/{feature}/` |
| DI modules | `app/src/main/java/.../di/modules/` |
| Tests | Mirror source path in `src/test/` |

## Git Workflow

**Branches:**
- `main` — Production (protected)
- `develop` — Staging (protected)
- Feature branches: `feature/add-login`, `bug/fix-crash` (lowercase, kebab-case)

**Commits:**
- Format: `[#123] Add feature`
- Capitalize first word, use present tense
- One logical change per commit
- If message contains "and", consider splitting

**Pull Requests:**
- Title: `[#ticket] Description`
- 2 approvals required (include Team Lead or senior)
- Target <500 lines changed
- Respond to reviews within 1 business day
- Target merge within 2-3 days

## Security

- API keys in `.properties` files (gitignored)
- Sensitive data → `EncryptedSharedPreferences`
- Never log tokens, passwords, or PII
- HTTPS only
- WebView JavaScript disabled by default
- Components `exported="false"` by default

## Key Guidelines for Agents

1. **Compose Only:** Build all UI with Jetpack Compose. Never suggest XML layouts.
2. **Architecture:** Respect module boundaries. Domain must have zero Android dependencies.
3. **State Management:** Use StateFlow exclusively. Never use LiveData.
4. **ViewModels:** Always extend `BaseViewModel`. Always inject with `hiltViewModel()`.
5. **Static Analysis:** Run `./gradlew detekt lint` on all modified files.
6. **Testing:** Write tests for new code. Use MockK, not Mockito.
7. **Code Style:** Follow Nimble Compass Kotlin conventions (4-space indent, trailing commas).
8. **Screen Pattern:** Public composable with ViewModel, private stateless content composable.

## Detekt Limits

| Rule | Limit |
|------|-------|
| Line length | 120 chars |
| Class size | 150 lines |
| Method size | 60 lines |
| Parameters | 5 max |
| Nested blocks | 3 max |
| Magic numbers | Only -1, 0, 1, 2 allowed |

## Boundaries

✅ **Required:**
- Follow app/data/domain layered architecture
- Use Hilt for DI, StateFlow for UI state
- Extend `BaseViewModel` for all ViewModels
- Write tests for new code (80% coverage)
- Use Jetpack Compose for all UI
- Follow Nimble Compass code conventions

⚠️ **Ask before:**
- Adding dependencies or modules
- Changing Detekt/ProGuard/CI config
- Modifying build variants

🚫 **Don't:**
- Commit secrets or credentials
- Push to `main`/`develop` directly
- Skip tests or static analysis
- Use LiveData (use StateFlow)
- Create ViewModels directly (use `hiltViewModel()`)
- Pass ViewModels to child composables
- Suggest XML-based layouts
- Add Android dependencies to domain module
