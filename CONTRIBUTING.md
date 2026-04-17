# Contributing

[日本語](./Japanese/CONTRIBUTING.md)

Thank you for your interest in contributing!

## Getting Started

### Prerequisites

- JDK 21
- IntelliJ IDEA (recommended)
- Discord desktop app (for testing)

### Build

```bash
./gradlew buildPlugin
```

The plugin ZIP will be in `build/distributions/`.

### Run in sandbox IDE

```bash
./gradlew runIde
```

### Verify

```bash
./gradlew verifyPlugin
./gradlew runPluginVerifier
```

## Submitting Changes

1. Fork the repository.
2. Create a branch from `main`.
3. Make your changes and commit using **English Semantic Commits** (`feat:`, `fix:`, `chore:`, `refactor:`, etc.).
4. Open a Pull Request against `main`.

## Commit Style

```
feat: add support for Swift language icon
fix: resolve null branch on project open
chore: update Gradle wrapper to 9.5
```

## Reporting Issues

Please open an issue with steps to reproduce, IDE version, and OS.
