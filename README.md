# Discord Rich Presence for JetBrains IDEs

A JetBrains plugin that displays your IDE activity as Discord Rich Presence.

[日本語](./Japanese/README.md)

---

## Features

- Displays the IDE logo and version
- Displays the language logo of the file you are editing
- Displays the project name and current Git branch
- Displays the file name you are editing
- Shows continuous playtime since the IDE was opened
- Reconnect notification when Discord is closed and reopened

## Supported IDEs

| IDE | Asset Key |
|---|---|
| IntelliJ IDEA (Community / Ultimate) | `idea` |
| WebStorm | `ws` |
| PyCharm (Community / Professional) | `pc` |
| GoLand | `go_land` |
| CLion | `cl` |
| Rider | `rd` |
| PhpStorm | `ps` |
| RubyMine | `rm` |
| RustRover | `rr` |
| DataGrip | `dg` |
| DataSpell | `ds` |
| dotCover | `dc` |
| dotMemory | `dm` |
| dotPeek | `dp` |
| dotTrace | `dt` |
| Android Studio | `as` |

## Supported Languages

Kotlin, JavaScript, TypeScript, React (JSX/TSX), Python, Go, C, C++, C#, PHP, Ruby, Rust, SQL, HTML, CSS, Markdown, JSON, YAML, Shell/Bash, Zsh, PowerShell, Dockerfile

Unsupported file types fall back to a generic text icon.

## Requirements

- JetBrains IDE 2023.3 (build 233) or later
- Discord desktop app (running)

## Installation

### JetBrains Marketplace

> Coming soon.

### Manual

1. Download the latest `.zip` from [Releases](../../releases).
2. In your IDE, go to **Settings → Plugins → ⚙ → Install Plugin from Disk**.
3. Select the downloaded `.zip` and restart the IDE.

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md).

## License

BSD 2-Clause — see [LICENSE](./LICENSE).

Third-party notices: [NOTICE.md](./NOTICE.md).
