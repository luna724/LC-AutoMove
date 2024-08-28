# LC-AutoMove
 Minecraft 1.8.9 で特定方向に歩きながらクリックするだけのMOD <br>
 `Hypixel Skyblock` の Garden での使用を想定しているが、普通に押すのと何かが違うわけではない <br>
また、[`LunaClient`](https://github.com/luna724/LunaClient) にて、複雑な動作を含む自動化機能を提供している

## 使い方 / installation
1. 普通に Forge MOD として導入する / Install the mod
2. ゲーム内で `/automove` と打ってヘルプを表示する / type `/automove` for in-game help
3. ヘルプを読んで使う / that helps all, good luck! but,.. it's japanese

## 非フォーカス時に使う方法
`F3` + `P` を押すと、非フォーカス時にゲームメニューが勝手に開かなくなるんだぞ！

## 機能説明 / command information
- `/automove toggle` <br>
AutoMoveを有効化/無効化する <br/>
そんだけ

/

Activate AutoMove

- `/automove hoverclick` <br>
左クリックの有無を設定する

/

configurate left-click

- `/automove setdirection <L/R/F/B/L?R?F?B?/reset>` <br>
歩く向きを設定する <br/>
Lは左、Rは右、Fは前、Bは後ろ <br>
`/automove setdirection` のみで移動なし <br>
`/automove setdirection reset` でリセット <br>
`/automove setdirection RF` などで 右+前 などを実装できる 

/

configure direction to walking <br>
L = Left, R = Right, F = Forward, B = Backward <br>
reset or not specify = won't moving <br>
Pro Tip: rf = Right + Forward

### セーフ機能 / is it Safe???
クライアントのキー情報をいじるだけなので安全性は高い <br />
だが、Macro という部類で禁止行為であることは確かなので、自己責任 <br />

/

you think.. it's suspicious/bannable? <br>
DON'T USE IT!


### 感謝 / Credits
- [dxxxxy/1.8.9ForgeTemplate](https://github.com/dxxxxy/1.8.9ForgeTemplate)
プロジェクトテンプレに使用

- [ChatGPT / GPT-4o](https://chatgpt.com/)
作者が理解していないメゾットの実装
コードの最適化
