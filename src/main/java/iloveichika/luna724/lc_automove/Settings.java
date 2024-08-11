package iloveichika.luna724.lc_automove;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class Settings {
    private static final String CATEGORY_GENERAL = "general";

    // 設定項目
    public String autoMoveDirection; // Literal: ["Left", "Right", "Forward", "NOT"]
    public boolean autoMoveClickEnable;
    public boolean autoMoveEnabled;

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
        autoMoveDirection = config.getString("AutoMoveDirection", CATEGORY_GENERAL, "Left", "自動移動の方向");
        autoMoveClickEnable = config.getBoolean("AutoMoveClickEnable", CATEGORY_GENERAL, true, "自動移動時にクリックをするかどうか");
        autoMoveEnabled = config.getBoolean("AutoMoveEnabled", CATEGORY_GENERAL, false, "自動移動のすてーたす ");

        if (config.hasChanged()) {
            config.save();
        }
    }

    // 設定を保存する
    public void saveConfig() {
        config.get(CATEGORY_GENERAL, "AutoMoveDirection", autoMoveDirection).set(autoMoveDirection);
        config.get(CATEGORY_GENERAL, "AutoMoveClickEnable", autoMoveClickEnable).set(autoMoveClickEnable);
        config.get(CATEGORY_GENERAL, "AutoMoveEnabled", autoMoveEnabled).set(autoMoveEnabled);

        if (config.hasChanged()) {
            config.save();
        }
    }
}