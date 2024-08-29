package iloveichika.luna724.lc_automove;

import iloveichika.luna724.lunaapi.LunaAPI;
import iloveichika.luna724.lunaapi.Main;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION)
public class MainMod {
    public static final String MODID = "lc_automove";
    public static final String NAME = "LC-AutoMove";
    public static final String VERSION = "2.1";

    private Settings settings;
    private AutoMove autoMove; // autoMove の定義を修正
    private LunaAPI lunaApi;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // 設定ファイルの場所を指定してSettingsを初期化
        File configFile = new File(event.getModConfigurationDirectory(), "luna724_automove.cfg");
        settings = new Settings(configFile);

        // AutoMoveを初期化
        lunaApi = Main.getInstance();
        autoMove = new AutoMove(settings, lunaApi); // autoMove の初期化

        // イベントバスに登録してTickイベントを監視
        MinecraftForge.EVENT_BUS.register(autoMove);
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // クライアントサイドのコマンドを登録
        ClientCommandHandler.instance.registerCommand(new CommandAutoMove(autoMove));
    }
}
