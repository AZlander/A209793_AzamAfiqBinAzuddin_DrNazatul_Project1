# Grove — Android Study Board App

Grove is an Android app that helps learners stay consistent with self-study by giving them a simple, focused tool to create study boards, organize subjects, and build daily learning habits — all in one place.

---

## Problem Statement

Many learners struggle to stay consistent with self-study because they lack a simple tool to track their goals and subjects. Grove solves this by letting users create study boards, organize subjects, and build daily learning habits, all in one app.

Grove is aligned with **UN Sustainable Development Goal 4 — Quality Education**, which aims to ensure inclusive and equitable quality education and promote lifelong learning opportunities for all.

---

## Features

### Home
- Personalized greeting with your name
- **Stats card** — live count of your study boards and unique subjects
- **Quick actions** — one-tap shortcuts to add a board, open the library, or search
- **Continue Studying** — horizontal scroll of your three most recent boards
- **SDG 4 Mission Card** — expandable card explaining Grove's alignment with Quality Education
- **Daily Study Tip** — rotates as you add more boards (e.g. Pomodoro technique, spaced repetition)

### Library
- Full scrollable list of all your study boards
- Tap any board to view its details
- Quick shortcut to create a new board

### Search
- Real-time search across board title, subject, and goal
- Dynamic result count and empty-state messages

### Board Detail
- Dedicated view for a single board showing title, subject, and goal

### Add Board
- Form to create a new study board with title, subject, and goal fields

### Profile
- View and edit your username and bio
- Changes sync across the whole app instantly

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Compose Navigation |
| State management | `StateFlow` + `ViewModel` |
| Architecture | Single-activity MVVM |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## Architecture

```
MainActivity
└── GroveApp (NavHost)
    ├── HomeScreen
    ├── SearchScreen
    ├── LibraryScreen
    ├── ProfileScreen
    ├── AddBoardScreen
    └── BoardDetailScreen/{boardId}

GroveViewModel          — shared ViewModel, single source of truth
├── userProfile: StateFlow<UserProfile>
└── boards: StateFlow<List<StudyBoard>>

data/dataSource.kt      — plain data classes (UserProfile, StudyBoard)
ui/theme/               — GroveTheme wrapping Material 3 colors & typography
```

All screens receive the same `GroveViewModel` instance. State is held as `StateFlow` and collected with `collectAsState()` in each composable.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 36
- JDK 17+
- A connected Android device or emulator (API 24+)

### Clone & Open

```bash
git clone https://github.com/AZlander/Grove.git
```

Open the project in Android Studio: **File → Open** → select the cloned folder.

### Build & Run

```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device/emulator
./gradlew installDebug

# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest
```

Or use the **Run** button in Android Studio to launch directly on a device or emulator.

---

## Project Structure

```
app/src/main/java/.../
├── MainActivity.kt              # Entry point, NavHost, BottomNavigationBar
├── data/
│   └── dataSource.kt            # UserProfile, StudyBoard data classes
├── screen/
│   ├── groveViewModel.kt        # Shared ViewModel
│   ├── homeScreen.kt
│   ├── searchScreen.kt
│   ├── libraryScreen.kt
│   ├── profileScreen.kt
│   ├── addBoardScreen.kt
│   └── boardDetailScreen.kt
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

---

## Author

**Azam Afiq Bin Azuddin**
Supervisor: Dr. Nazatul
