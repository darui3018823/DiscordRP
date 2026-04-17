# Discord Rich Presence へのコントリビューション

> 📖 **Languages:** [English](../CONTRIBUTING.md) | [日本語](./CONTRIBUTING.md)

Discord Rich Presence へのコントリビューションに興味をお持ちいただきありがとうございます！コミュニティからの貢献を歓迎します。このドキュメントは、プロジェクトへの貢献方法についてのガイドラインと手順を提供します。

## 行動規範

このプロジェクトは[行動規範](./CODE_OF_CONDUCT.md)に基づいています。プロジェクトに参加することで、その条件に従うことに同意します。

## コントリビューションの方法

### バグレポート

バグを見つけた場合、以下の情報を含むイシューを作成してください：

- **説明**: バグについて明確かつ簡潔な説明
- **再現手順**: バグを再現する手順
- **期待動作**: 何が起こるはずだったか
- **実際の動作**: 実際に何が起こったか
- **環境**: OS、IDE 名とバージョン、Discord バージョン
- **スクリーンショット/ログ**: 該当する場合、エラーメッセージや IDE ログを含める（`ヘルプ → エクスプローラーでログを表示`）

### 機能提案

新しい機能や改善についての提案を歓迎します：

- **説明**: 機能について明確かつ簡潔な説明
- **動機**: この機能が有用だと考える理由
- **代替案**: 検討した代替アプローチ
- **追加情報**: その他の関連情報

### プルリクエストの提出

1. **リポジトリをフォークする**: GitHub の「Fork」ボタンをクリック

2. **フォークをクローンする**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/DiscordRP.git
   cd DiscordRP
   ```

3. **フィーチャーブランチを作成する**:
   ```bash
   git checkout -b feat/your-feature-name
   ```
   またはバグ修正の場合:
   ```bash
   git checkout -b fix/your-bug-fix
   ```

4. **変更を実装する**: 機能またはバグ修正を実装

5. **コミットメッセージ規約に従う**:
   [Conventional Commits](https://www.conventionalcommits.org/) に従います：
   - `feat: 新機能を追加`
   - `fix: バグを修正`
   - `docs: ドキュメントを更新`
   - `refactor: コードをリファクタリング`
   - `chore: 依存関係を更新`

6. **変更をビルド・検証する**:
   ```bash
   ./gradlew buildPlugin
   ./gradlew verifyPlugin
   ```

7. **フォークにプッシュする**:
   ```bash
   git push origin feat/your-feature-name
   ```

8. **プルリクエストを作成する**: GitHub で「New Pull Request」をクリックして、以下を記載：
   - 変更を説明する明確なタイトル
   - 何を変更したかと理由についての詳細説明
   - 関連するイシューへの参照（ある場合）

## 開発環境セットアップ

### 前提条件

- JDK 21
- IntelliJ IDEA（推奨）
- Discord デスクトップアプリ（動作確認用）

### ソースからビルド

```bash
git clone https://github.com/darui3018823/DiscordRP.git
cd DiscordRP
./gradlew buildPlugin
```

プラグインの ZIP が `build/distributions/` に出力されます。

### サンドボックス IDE で実行

```bash
./gradlew runIde
```

### プラグインの検証

```bash
./gradlew verifyPlugin
./gradlew runPluginVerifier
```

## プロジェクト構成

```
DiscordRP/
├── src/main/
│   ├── kotlin/com/github/darui3018823/discordrp/
│   │   ├── DiscordRPService.kt     # IPC 接続管理 (ApplicationService)
│   │   ├── ProjectRPService.kt     # プレゼンス状態と更新 (ProjectService)
│   │   ├── FileEditorListener.kt   # ファイル切り替え検知
│   │   ├── ProjectListener.kt      # プロジェクト開閉検知
│   │   ├── IdeDetector.kt          # IDE → アセットキー マッピング
│   │   ├── LanguageDetector.kt     # 言語 → アセットキー マッピング
│   │   └── GitBranchDetector.kt    # Git ブランチ取得
│   └── resources/META-INF/
│       └── plugin.xml
├── .github/workflows/              # GitHub Actions
├── Japanese/                       # 日本語ドキュメント
├── build.gradle.kts
└── settings.gradle.kts
```

## プルリクエストレビュープロセス

1. PR はプロジェクトメンテナーによってレビューされます
2. 変更を要求されたり、質問されたりする場合があります
3. 承認されたら、PR はマージされます
4. 貢献は次のリリースでクレジットされます

## リリースプロセス

[セマンティックバージョニング](https://semver.org/) に従います：
- メジャーバージョン: 後方互換性のない変更
- マイナーバージョン: 後方互換性のある新機能
- パッチバージョン: 後方互換性のあるバグ修正

## ご質問は？

以下の方法でお気軽にどうぞ：
- `question` ラベル付きのイシューを開く
- [contact@daruks.com](mailto:contact@daruks.com) までお問い合わせ

Discord Rich Presence へのコントリビューションをお待ちしています！
