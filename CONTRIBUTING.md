# Contributing to Discord Rich Presence

> 📖 **Languages:** [English](./CONTRIBUTING.md) | [日本語](./Japanese/CONTRIBUTING.md)

Thank you for your interest in contributing to Discord Rich Presence! We welcome contributions from the community. This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

Please note that this project is governed by our [Code of Conduct](./CODE_OF_CONDUCT.md). By participating in this project, you agree to abide by its terms.

## How to Contribute

### Reporting Bugs

If you encounter a bug, please create an issue with the following information:

- **Description**: A clear and concise description of the bug
- **Steps to Reproduce**: Steps to reproduce the behavior
- **Expected Behavior**: What you expected to happen
- **Actual Behavior**: What actually happened
- **Environment**: Your OS, IDE name and version, Discord version
- **Screenshots/Logs**: If applicable, include error messages or IDE logs (`Help → Show Log in Explorer`)

### Suggesting Enhancements

We welcome suggestions for new features or improvements:

- **Description**: A clear and concise description of the enhancement
- **Motivation**: Why you believe this enhancement would be useful
- **Alternative Solutions**: Any alternative approaches you've considered
- **Additional Context**: Any other relevant information

### Submitting Pull Requests

1. **Fork the Repository**: Click the "Fork" button on GitHub

2. **Clone Your Fork**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/DiscordRP.git
   cd DiscordRP
   ```

3. **Create a Feature Branch**:
   ```bash
   git checkout -b feat/your-feature-name
   ```
   or for bug fixes:
   ```bash
   git checkout -b fix/your-bug-fix
   ```

4. **Make Your Changes**: Implement your feature or bug fix

5. **Follow Commit Message Conventions**:
   We follow [Conventional Commits](https://www.conventionalcommits.org/):
   - `feat: add new feature`
   - `fix: fix a bug`
   - `docs: update documentation`
   - `refactor: refactor code`
   - `chore: update dependencies`

6. **Build and Verify Your Changes**:
   ```bash
   ./gradlew buildPlugin
   ./gradlew verifyPlugin
   ```

7. **Push to Your Fork**:
   ```bash
   git push origin feat/your-feature-name
   ```

8. **Create a Pull Request**: Click "New Pull Request" on GitHub and provide:
   - A clear title describing your changes
   - A detailed description of what you changed and why
   - References to related issues (if any)

## Development Setup

### Prerequisites

- JDK 21
- IntelliJ IDEA (recommended)
- Discord desktop app (for testing)

### Building from Source

```bash
git clone https://github.com/darui3018823/DiscordRP.git
cd DiscordRP
./gradlew buildPlugin
```

The plugin ZIP will be output to `build/distributions/`.

### Running in a Sandbox IDE

```bash
./gradlew runIde
```

### Verifying the Plugin

```bash
./gradlew verifyPlugin
./gradlew runPluginVerifier
```

## Project Structure

```
DiscordRP/
├── src/main/
│   ├── kotlin/com/github/darui3018823/discordrp/
│   │   ├── DiscordRPService.kt     # IPC connection management (ApplicationService)
│   │   ├── ProjectRPService.kt     # Presence state and updates (ProjectService)
│   │   ├── FileEditorListener.kt   # File switch detection
│   │   ├── ProjectListener.kt      # Project open/close detection
│   │   ├── IdeDetector.kt          # IDE → asset key mapping
│   │   ├── LanguageDetector.kt     # Language → asset key mapping
│   │   └── GitBranchDetector.kt    # Git branch detection
│   └── resources/META-INF/
│       └── plugin.xml
├── .github/workflows/              # GitHub Actions
├── Japanese/                       # Japanese documentation
├── build.gradle.kts
└── settings.gradle.kts
```

## Pull Request Review Process

1. Your PR will be reviewed by the project maintainer
2. We may request changes or ask clarifying questions
3. Once approved, your PR will be merged
4. Your contribution will be credited in the next release

## Release Process

We follow [Semantic Versioning](https://semver.org/):
- MAJOR version for incompatible changes
- MINOR version for new backwards-compatible functionality
- PATCH version for backwards-compatible bug fixes

## Questions?

Feel free to:
- Open an issue with the label `question`
- Contact us at [contact@daruks.com](mailto:contact@daruks.com)

Thank you for contributing to Discord Rich Presence!
