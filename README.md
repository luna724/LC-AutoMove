# LC-AutoMove
 Minecraft 1.8.9 で特定方向に歩きながらクリックするだけのMOD <br>
 主に `ChatTriggers` などでの使用を想定しているが、普通に押すのと何かが違うわけではない <br>
- 使用例: [LunaClient Legacy](https://github.com/luna724/LunaClient/releases/tag/1.1.4sf)
- 使用例: [LC-Gardening](https://github.com/luna724/lcg) 

## 入れ方
1. 普通に Forge MOD として導入する

## 機能説明 / command information
- `/automove toggle` <br>
AutoMoveを有効化/無効化する <br/>

- `/automove hoverclick` <br>
左クリックの有無を設定する

- `/automove setdirection <L?R?F?B?/reset>` <br>
歩く向きを設定する <br/>
Lは左、Rは右、Fは前、Bは後ろ <br>
`/automove setdirection` のみで移動なし <br>
`/automove setdirection reset` でリセット <br>
`/automove setdirection RF` などで 右+前 などを実装できる 

- `/automove <start/stop>` <br>
スタートとストップを指定した版の `/automove toggle`

- `/automove safemode` <br>
ワールド移動時に自動的に無効化する機能のオンオフ

- `/automove toggleinfo` <br>
動作時の内部的挙動をチャットに表示するかどうか

- `/automove setyaw <Yaw>` <br>
Yawを指定方向へ動かす
それだけの機能
 
### セーフ機能 / is it Safe???
クライアントのキー情報をいじるだけなので安全性は高い <br />
だが、Macro という部類で禁止行為であることは確かなので、自己責任 <br />

- [Changelogs](/Changelogs.md)
