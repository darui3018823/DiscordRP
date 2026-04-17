# コントリビューション

[English](../CONTRIBUTING.md)

コントリビューションに興味を持っていただきありがとうございます！

## はじめに

### 前提条件

- JDK 21
- IntelliJ IDEA（推奨）
- Discord デスクトップアプリ（動作確認用）

### ビルド

```bash
./gradlew buildPlugin
```

プラグインの ZIP が `build/distributions/` に生成されます。

### サンドボックス IDE で実行

```bash
./gradlew runIde
```

### 検証

```bash
./gradlew verifyPlugin
./gradlew runPluginVerifier
```

## 変更の提出

1. リポジトリをフォークする。
2. `main` ブランチからブランチを作成する。
3. 変更を行い、**英語の Semantic Commit** でコミットする（`feat:`, `fix:`, `chore:`, `refactor:` など）。
4. `main` に対してプルリクエストを作成する。

## コミットスタイル

```
feat: add support for Swift language icon
fix: resolve null branch on project open
chore: update Gradle wrapper to 9.5
```

## 問題の報告

再現手順・IDE バージョン・OS を記載した上で Issue を作成してください。
