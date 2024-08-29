package iloveichika.luna724.lc_automove;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ChatComponentText;

public class ChatLib {
    private static Settings settings;
    public ChatLib(Settings setting) {
        settings = setting;
    }

    private static void sendClientSideMessage(ChatComponentText components) {
        if (!settings.autoMoveInfo) { return; }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) {
            if (!(mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiIngameMenu)) {
                System.out.println("prevents send data. Reason: Due to current screen");
                return;
            }
        }

        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(components);
        }
    }
    private static final String infoHeader = "§7[§dAutoMove§7]: §r§7";

    public static void sendStatusInfo(String msg) {
        ChatComponentText chat = new ChatComponentText(
                infoHeader + "§7[§fINFO§7]: §r§7" + msg
        );
        sendClientSideMessage(chat);
    }

    public static void sendStatusWarn(String msg) {
        ChatComponentText chat = new ChatComponentText(
                infoHeader + "§7[§cWARN§7]: §r§7" + msg
        );
        sendClientSideMessage(chat);
    }

    public static void sendStatusCritical(String msg) {
        ChatComponentText chat = new ChatComponentText(
                infoHeader + "§7[§4§lCRITICAL§r§7]: " + msg
        );
        sendClientSideMessage(chat);
    }
}
