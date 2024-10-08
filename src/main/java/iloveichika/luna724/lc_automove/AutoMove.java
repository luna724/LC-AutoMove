package iloveichika.luna724.lc_automove;

import iloveichika.luna724.lunaapi.LunaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@SideOnly(Side.CLIENT)
public class AutoMove {
    public final Settings settings;
    private final LunaAPI lunaAPI;
    public final RotationManager rotationManager;
    public final ChatLib chatLib;

    public AutoMove(Settings settings, LunaAPI lunaAPI, RotationManager rotationManager, ChatLib chatLib) {
        this.settings = settings;
        this.lunaAPI = lunaAPI;
        this.rotationManager = rotationManager;
        this.chatLib = chatLib;
    }

    private int[] getKeyBinds() {
        /*0=Forward, 1=Backward, 2=Left, 3=Right, 4=Attack*/
        Minecraft mc = Minecraft.getMinecraft();
        int[] binds = new int[5];
        binds[0] = mc.gameSettings.keyBindForward.getKeyCode();
        binds[1] = mc.gameSettings.keyBindBack.getKeyCode();
        binds[2] = mc.gameSettings.keyBindLeft.getKeyCode();
        binds[3] = mc.gameSettings.keyBindRight.getKeyCode();
        binds[4] = mc.gameSettings.keyBindAttack.getKeyCode();

        return binds;
    }

    public List<String> getMovingKey() {
        int keyBytes = settings.autoMoveDirection;
        List<String> directions = new ArrayList<>();

        if ((keyBytes & DIRECTION_LEFT) != 0) {
            directions.add("Left");
        }
        if ((keyBytes & DIRECTION_RIGHT) != 0) {
            directions.add("Right");
        }
        if ((keyBytes & DIRECTION_FORWARD) != 0) {
            directions.add("Forward");
        }
        if ((keyBytes & DIRECTION_BACKWARD) != 0) {
            directions.add("Backward");
        }

        // リストをString配列に変換して返す
        return directions;
    }

    // 各移動方向用のフラグ値
    public int DIRECTION_LEFT = 1;      // 0001
    public int DIRECTION_RIGHT = 2;     // 0010
    public int DIRECTION_FORWARD = 4;   // 0100
    public int DIRECTION_BACKWARD = 8;  // 1000

    public void AutoMovingStop() {
        System.out.println("Stopping AutoMoving..");
        int[] keyCodes = getKeyBinds();

        KeyBinding.setKeyBindState(keyCodes[0], false);
        KeyBinding.setKeyBindState(keyCodes[1], false);
        KeyBinding.setKeyBindState(keyCodes[2], false);
        KeyBinding.setKeyBindState(keyCodes[3], false);
        KeyBinding.setKeyBindState(keyCodes[4], false);
        chatLib.sendStatusInfo("[Main]: Stopped AutoMove");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        // 設定が無効なら
        if (!settings.autoMoveEnabled) { return; }

        Minecraft mc = Minecraft.getMinecraft();
        // GUIが開いている場合は動作を行わない
        if (mc.currentScreen != null) {
            //System.out.println("Current screen != null!. Current screen: " + mc.currentScreen.toString());
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) { return; }

        List<String> movingKeys = getMovingKey();
        int[] keyCodes = getKeyBinds();

        // 値に応じキーを押す
        if (movingKeys.contains("Forward")) {
            KeyBinding.setKeyBindState(keyCodes[0], true);
            KeyBinding.onTick(keyCodes[0]);
        }
        if (movingKeys.contains("Backward")) {
            KeyBinding.setKeyBindState(keyCodes[1], true);
            KeyBinding.onTick(keyCodes[1]);
        }
        if (movingKeys.contains("Left")) {
            KeyBinding.setKeyBindState(keyCodes[2], true);
            KeyBinding.onTick(keyCodes[2]);
        }
        if (movingKeys.contains("Right")) {
            KeyBinding.setKeyBindState(keyCodes[3], true);
            KeyBinding.onTick(keyCodes[3]);
        }

        if (settings.autoMoveClickEnable) {
            KeyBinding.setKeyBindState(keyCodes[4], true);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        /* ワールドがロードされたら動作停止 */
        if (!settings.autoMoveStopWhenServerSwap) { return; }

        System.out.println("Stopped AutoMoving by Safety Module.");
        settings.autoMoveEnabled = false;
        AutoMovingStop();
    }

    public String sendDataToLunaClient(String request, String[] args) {
        List<String> movingKey = getMovingKey();
        boolean hasClick = settings.autoMoveClickEnable;
        boolean isEnabled = settings.autoMoveEnabled;
        String message;
        String endsIdentifier = "§7**LC-Identifier";

        /* Pattern (実際に改行はない)
        {Identifier} AutoMove Options:
        $ENABLE?{isEnabled}&$CLICK?{hasClick}&$MOVE?{movingKey}
        **LC-Identifier
         */
        // デフォ
        message = LunaAPI.Identifier + "AutoMove Options: $" + isEnabled + "&$" + hasClick + "&$" + movingKey.toString() + endsIdentifier;
        if (request.equalsIgnoreCase("isEnabled?")) {
            message = LunaAPI.Identifier + "isEnabled? @" + isEnabled + endsIdentifier;
        }
        if (request.equalsIgnoreCase("hasClick?")) {
            message = LunaAPI.Identifier + "hasClick? @" + hasClick + endsIdentifier;
        }
        if (request.equalsIgnoreCase("movingKey?")) {
            message = LunaAPI.Identifier + "movingKey? @" + movingKey + endsIdentifier;
        }
        if (request.equalsIgnoreCase("VERSION?")) {
            message = LunaAPI.Identifier + "VERSION? @" + MainMod.VERSION + endsIdentifier;
        }
        if (request.equalsIgnoreCase("POST") && args.length > 2) {
            String received = args[2];
            message = LunaAPI.Identifier + "STATUS? @200" + endsIdentifier;
        }
        if (request.equalsIgnoreCase("rotateYaw") && args.length > 3) {
            String stringYaw = args[2];
            String stringTaken = args[3];
            float yaw = parseFloat(stringYaw);
            int taken = parseInt(stringTaken);
            rotationManager.startYawChanger(yaw, taken);
            message = LunaAPI.Identifier + "Changing Yaw to " + yaw + ". Taking " + taken + "ticks" + endsIdentifier;
        }
        if (request.equalsIgnoreCase("sendStatus") && args.length > 3) {
            String status = args[2];
            String[] statusStringArray = Arrays.copyOfRange(args, 3, args.length);
            String statusString = String.join(" ", statusStringArray);

            if (status.equalsIgnoreCase("info")) {
                chatLib.sendStatusInfo(statusString);
            }
            else if (status.equalsIgnoreCase("warn")) {
                chatLib.sendStatusWarn(statusString);
            }
            else if (status.equalsIgnoreCase("critical")) {
                chatLib.sendStatusCritical(statusString);
            }
            message = LunaAPI.Identifier + "sent by. LunaClient - " + status.toUpperCase() + endsIdentifier;
        }

        return message;
    }
}