# LC-AutoMove
 Minecraft 1.8.9 で特定方向に歩きながらクリックするだけのMOD <br>
 `Hypixel Skyblock` の Garden での使用を想定しているが、普通に押すのと何かが違うわけではない


## 使い方 / installation
1. 普通に Forge MOD として導入する / Install the mod
2. ゲーム内で `/automove` と打ってヘルプを表示する / type `/automove` for in-game help
3. ヘルプを読んで使う / that helps all, good luck!

## 機能説明 / command information
- `/automove toggle`
AutoMoveを有効化する <br/>
そんだけ

/

Activate AutoMove

- `/automove hoverclick`
左クリックの有無を設定する

/

configurate left-click

- `/automove setdirection <L/R/F>`
歩く向きを設定する <br/>
Lは左、Rは右、Fは前
後ろなんて向かずに生きていけ？

/

configure direction to walking
L = Left, R = Right, F = Forward
Backward isN't supported.


### セーフ機能 / is it Safe???
Java の Robot を使用したエミュレーションを行っているだけだから <br />
何かが押されただけで止まったりして非常に安全 <br />
ただ `Hypixel` とかで使うなら [LunaClient](https://github.com/luna724/LunaClient) の `AntiServerMoving` を入れないとバレるかもね <br>

/

you think.. it's suspicious? <br>
DON'T USE IT!


### 感謝 / Credits
- [dxxxxy/1.8.9ForgeTemplate](https://github.com/dxxxxy/1.8.9ForgeTemplate)
プロジェクトテンプレに使用

- [ChatGPT / GPT-4o](https://chatgpt.com/)
作者が理解していないメゾットの実装
コードの最適化
