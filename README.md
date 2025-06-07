# EcoEgg

EcoEggは、MinecraftのSpigotプラグインで、MOBを卵に変換する機能を提供します。特別な魔道書を使用してMOBをスポーンエッグに変換し、後で孵化させることができます。

## 機能

### 主要機能
- **MOB卵化**: 魔道書を使ってMOBをスポーンエッグに変換
- **設定可能なドロップテーブル**: MOB毎に個別のドロップ率と数量を設定
- **ルートボーナスシステム**: レベル1-3のルートボーナス効果
- **WorldGuard連携**: 地域保護プラグインとの統合（オプション）
- **包括的なコマンドシステム**: 管理とプレイヤー向けの豊富なコマンド

### 対応MOB
BAT, BEE, BLAZE, CAVE_SPIDER, CHICKEN, CAT, COD, COW, CREEPER, DOLPHIN, DONKEY, DROWNED, ELDER_GUARDIAN, ENDERMAN, ENDERMITE, EVOKER, FOX, GHAST, GUARDIAN, HORSE, HUSK, LLAMA, MAGMA_CUBE, MOOSHROOM, MULE, OCELOT, PANDA, PARROT, PHANTOM, PIG, PILLAGER, POLAR_BEAR, PUFFERFISH, RABBIT, RAVAGER, SALMON, SHEEP, SHULKER, SILVERFISH, SKELETON, SKELETON_HORSE, SLIME, SPIDER, SQUID, STRAY, TRADER_LLAMA, TROPICAL_FISH, TURTLE, VEX, VILLAGER, VINDICATOR, WANDERING_TRADER, WITCH, WITHER_SKELETON, WOLF, ZOMBIE, ZOMBIE_HORSE, ZOMBIE_PIGMAN, ZOMBIE_VILLAGER

## インストール

### 必要条件
- **Minecraft**: 1.15以上
- **Spigot/Paper**: 1.20.4以上
- **Java**: 8以上
- **EcoFramework**: 必須依存関係
- **WorldGuard**: オプション（地域保護機能を使用する場合）

### インストール手順
1. [EcoFramework](https://github.com/ecolight15/EcoFramework)をダウンロードし、`plugins`フォルダに配置
2. EcoEggプラグイン(.jar)を`plugins`フォルダに配置
3. サーバーを再起動
4. 必要に応じて設定ファイルを編集

## 設定

### config.yml

```yaml
# ドロップテーブル設定例
droptable:
  ender_dragon:
    amount: 3        # ドロップ数
    rate: 1          # ドロップ率（1/rate）
    lootbonus:
      level1: 1.4    # ルートボーナスレベル1
      level2: 1.7    # ルートボーナスレベル2
      level3: 2.0    # ルートボーナスレベル3
  witch:
    amount: 1
    rate: 100
    lootbonus:
      level1: 2.0
      level2: 3.0
      level3: 4.0

# 魔道書設定
title: '魔道書「えこたまご」'      # 本のタイトル
name: '魔道書「えこたまご」'       # アイテム表示名
author: '神官えこ'                # 著者名
pages:                            # ページ内容
  - 'この書物は生物を卵化する効果があります。対象に向かって右クリックで発動。使用後この書物は消滅します。次ページは対象生物一覧。'
  - 'BAT, BEE, BLAZE, CAVE_SPIDER, ...'

# WorldGuard連携
WorldGuard:
  Enabled: false    # true で有効化

# 除外エンティティ
IgnoreEntity:
  - 'WARDEN'        # 変換対象外のMOB
```

## 使用方法

### 基本的な使い方
1. `/ece book` コマンドで魔道書を入手
2. 変換したいMOBに向かって魔道書を右クリック
3. MOBがスポーンエッグに変換されます
4. スポーンエッグを設置して右クリックでMOBを再生成

### 管理者向け
- `/ece reload` - 設定ファイルを再読み込み
- `/ece info <プレイヤー名>` - プレイヤーの情報表示
- `/ece set <オプション> <値>` - 設定値を変更

## コマンド

| コマンド | 説明 | 権限 |
|---------|------|------|
| `/ece` | メインコマンド | `ecoegg` |
| `/ece reload` | 設定再読み込み | `ecoegg.reload` |
| `/ece info` | 情報表示 | `ecoegg.info` |
| `/ece infoegg` | 卵情報表示 | `ecoegg.infoegg` |
| `/ece get` | アイテム取得 | `ecoegg.get` |
| `/ece set` | 設定変更 | `ecoegg.set` |
| `/ece book` | 魔道書取得 | `ecoegg.book` |
| `/ece bookupdate` | 魔道書更新 | `ecoegg.bookupdate` |
| `/ece egg` | 卵管理 | `ecoegg.egg` |

## 権限

- `ecoegg` - 基本権限
- `ecoegg.reload` - 設定再読み込み権限
- `ecoegg.info` - 情報表示権限
- `ecoegg.infoegg` - 卵情報表示権限
- `ecoegg.get` - アイテム取得権限
- `ecoegg.set` - 設定変更権限
- `ecoegg.book` - 魔道書取得権限
- `ecoegg.bookupdate` - 魔道書更新権限
- `ecoegg.egg` - 卵管理権限

## WorldGuard連携

WorldGuardプラグインがインストールされている場合、地域保護機能を利用できます：

1. `config.yml`で`WorldGuard.Enabled: true`に設定
2. 地域に`use-ecoegg`フラグを設定してMOB変換の可否を制御

```
/rg flag <地域名> use-ecoegg allow|deny
```

## トラブルシューティング

### よくある問題

**Q: 魔道書でMOBが変換されない**
A: 以下を確認してください：
- プレイヤーに適切な権限があるか
- WorldGuard地域でMOB変換が許可されているか
- 対象MOBが除外リストに含まれていないか

**Q: 設定が反映されない**
A: `/ece reload`コマンドで設定を再読み込みしてください

## 開発情報

- **バージョン**: 1.13
- **作者**: ecolight
- **ライセンス**: GNU Lesser General Public License v3.0
- **依存関係**: EcoFramework 0.21+
- **対応API**: Bukkit API 1.15+

## ライセンス

このプロジェクトは[GNU Lesser General Public License v3.0](LICENSE)の下で配布されています。

## 貢献

バグ報告や機能提案は、GitHubのIssuesで受け付けています。プルリクエストも歓迎します。

## リンク

- [EcoFramework](https://github.com/ecolight15/EcoFramework) - 必須依存関係
- [WorldGuard](https://dev.bukkit.org/projects/worldguard) - オプション依存関係