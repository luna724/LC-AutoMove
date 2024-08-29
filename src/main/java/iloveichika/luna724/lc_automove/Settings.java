package iloveichika.luna724.lc_automove;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class Settings {
    private static final String CATEGORY_GENERAL = "general";

    // 設定項目

    public int autoMoveDirection; // Literal: ["Left", "Right", "Forward", "Backward", "NaN"]
    public boolean autoMoveClickEnable;
    public boolean autoMoveEnabled;
    public boolean autoMoveStopWhenServerSwap;

    // Configurationオブジェクト
    private Configuration config;

    // コンストラクタで設定を読み込む
    public Settings(File configFile) {
        // コンフィグファイルを読み込む
        config = new Configuration(configFile);
        loadConfig();
    }

    // 設定を読み込む
    public void loadConfig() {
        config.load();

        // 設定項目の読み込み（もしくはデフォルト値の設定）
        autoMoveDirection = config.getInt("AutoMoveDirection", CATEGORY_GENERAL, 0, -2147483647, 2147483647, "自動移動の方向");
        autoMoveClickEnable = config.getBoolean("AutoMoveClickEnable", CATEGORY_GENERAL, true, "自動移動時にクリックをするかどうか");
        autoMoveEnabled = config.getBoolean("AutoMoveEnabled", CATEGORY_GENERAL, false, "自動移動のすてーたす ");
        autoMoveStopWhenServerSwap = config.getBoolean("AutoMoveStopWhenServerSwap", CATEGORY_GENERAL, true, "");
    }

    // 設定を保存する
    public void saveConfig() {
        config.get(CATEGORY_GENERAL, "AutoMoveDirection", autoMoveDirection).set(autoMoveDirection);
        config.get(CATEGORY_GENERAL, "AutoMoveClickEnable", autoMoveClickEnable).set(autoMoveClickEnable);
        config.get(CATEGORY_GENERAL, "AutoMoveEnabled", autoMoveEnabled).set(autoMoveEnabled);
        config.get(CATEGORY_GENERAL, "AutoMoveStopWhenServerSwap", autoMoveStopWhenServerSwap).set(autoMoveStopWhenServerSwap);

        System.out.println("[LC-AutoMove]: Saving config..");
        config.save();
    }
}