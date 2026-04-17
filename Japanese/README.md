# Discord Rich Presence for JetBrains IDEs

JetBrains IDE の操作状況を Discord Rich Presence として表示するプラグインです。

[English](../README.md)

---

## 機能

- IDE ロゴとバージョンの表示
- 編集中ファイルの言語ロゴの表示
- プロジェクト名と Git ブランチ名の表示
- 編集中のファイル名の表示
- IDE を開いてからの連続プレイ時間の表示
- Discord が終了・再起動された際の再接続通知

## 対応 IDE

| IDE | アセットキー |
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

## 対応言語

Kotlin, JavaScript, TypeScript, React (JSX/TSX), Python, Go, C, C++, C#, PHP, Ruby, Rust, SQL, HTML, CSS, Markdown, JSON, YAML, Shell/Bash, Zsh, PowerShell, Dockerfile

未対応のファイル種別はテキストアイコンにフォールバックします。

## 動作要件

- JetBrains IDE 2023.3 (build 233) 以降
- Discord デスクトップアプリ（起動中）

## インストール

### JetBrains Marketplace

> 近日公開予定。

### 手動インストール

1. [Releases](../../releases) から最新の `.zip` をダウンロード。
2. IDE で **設定 → プラグイン → ⚙ → ディスクからプラグインをインストール** を選択。
3. ダウンロードした `.zip` を選択し、IDE を再起動。

## コントリビューション

[CONTRIBUTING.md](./CONTRIBUTING.md) を参照してください。

## ライセンス

BSD 2-Clause — [LICENSE](../LICENSE) を参照。

サードパーティ表記: [NOTICE.md](./NOTICE.md)
