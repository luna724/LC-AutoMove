package iloveichika.luna724.lc_automove;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class AutoMove {

    public final Settings settings;
    private Robot robot;
    public AutoMove(Settings settings) {
        this.settings = settings;

        try {
            robot = new Robot(); // Robotインスタンスを作成
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @SubscribeEvent
    public void AutoMoving() {
        System.out.println("AutoMoving called. autoMoveEnabled: " + settings.autoMoveEnabled);
        if (!settings.autoMoveEnabled) {
            System.out.println("Stopping movement.");
            robot.keyRelease(KeyEvent.VK_W);
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_D);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            // robot.keyRelease(KeyEvent.VK_S);
        } else {

            // インベントリやGUIが開いている場合は動作を停止
//            if (Minecraft.getMinecraft().currentScreen != null) {
//                // stopMoving();
//                System.out.println("Movement stopped due to open GUI.");
//                return;
//            }

            // 設定が "NOT" であれば移動しない
            if ("NOT".equals(settings.autoMoveDirection)) {
                // stopMoving();
                System.out.println("Movement direction is set to NOT, stopping movement.");
                return;
            }

            // 現在の設定に基づいて移動方向を決定
            int keyToPress = -1;
            switch (settings.autoMoveDirection) {
                case "Left":
                    keyToPress = KeyEvent.VK_A;
                    break;
                case "Right":
                    keyToPress = KeyEvent.VK_D;
                    break;
                case "Forward":
                    keyToPress = KeyEvent.VK_W;
                    break;
                default:
                    break;
            }

            // 他のキーをリセット
            // stopMoving();

            if (keyToPress != -1) {
                // 指定された方向のキーを押す
                System.out.println("Pressing key: " + keyToPress);
                robot.keyPress(keyToPress);
            }

            // クリックを有効にする設定の場合
            if (settings.autoMoveClickEnable) {
                // 左クリックをエミュレート
                System.out.println("Mouse click enabled.");
                robot.mousePress(KeyEvent.BUTTON1_MASK);
            } else {
                robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            }
        }
    }

    private void stopMoving() {
        // すべての移動キーをリリース
        System.out.println("Releasing all movement keys.");
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_S);
    }
}