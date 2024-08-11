package iloveichika.luna724.lc_automove;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class CommandAutoMove extends CommandBase {

    private final AutoMove autoMove;

    public CommandAutoMove(AutoMove autoMove) {
        this.autoMove = autoMove;
    }

    @Override
    public String getCommandName() {
        return "automove";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/automove <toggle|setdirection|hoverclick> {L/R/F}";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            String helpMessage = "§f[§dLC-AutoMove§f] Help\n" +
                                 "§6 - §f/automove toggle\n" +
                                 "§e AutoMove の切り替え\n" +
                                 "§6 - §f/automove setdirection <L/R/F>\n" +
                                 "§e 歩く向きの設定 L = 左, R = 右, F = 前\n" +
                                 "§6 - §f/automove hoverclick\n" +
                                 "§e 左クリックするかどうか";
            sender.addChatMessage(new ChatComponentText(helpMessage));
        }

        if ("toggle".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveEnabled = true; //!autoMove.settings.autoMoveEnabled;
            autoMove.AutoMoving();  // 自動移動関数を呼び出し

            if (autoMove.settings.autoMoveEnabled) {
                String msg = "[§dLC-AutoMove§f]: §6AutoMove Started.";
                sender.addChatMessage(new ChatComponentText(msg));
            } else {
                String msg = "[§dLC-AutoMove§f]: §6AutoMove Stopped.";
                sender.addChatMessage(new ChatComponentText(msg));
            }
        } else if ("hoverclick".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveClickEnable = !autoMove.settings.autoMoveClickEnable;
            if (autoMove.settings.autoMoveClickEnable) {
                String msg = "[§dLC-AutoMove§f]: §6hoverClick Enabled.";
                sender.addChatMessage(new ChatComponentText(msg));
            } else {
                String msg = "[§dLC-AutoMove§f]: §6hoverClick Disabled.";      //      sender.addChatMessage(new ChatComponentText(msg));
                sender.addChatMessage(new ChatComponentText(msg));
            }
        } else if ("setdirection".equalsIgnoreCase(args[0])) {
            if (args.length < 2) {
                sender.addChatMessage(new ChatComponentText("引数 <L/R/F> が見つかりませんでした"));
                return;
            }
            List<String> availableDirection = Arrays.asList("l", "r", "f");
            if (!availableDirection.contains(args[1].toLowerCase())) {
                sender.addChatMessage(new ChatComponentText("正しくない引数が渡されました。 <L/R/F> のみが使用可能です。"));
                return;
            }
            // あるならそれに変換する
            switch (args[1].toLowerCase()) {
                case "l":
                    autoMove.settings.autoMoveDirection = "Left";
                    break;
                case "r":
                    autoMove.settings.autoMoveDirection = "Right";
                    break;
                case "f":
                    autoMove.settings.autoMoveDirection = "Forward";
                    break;
                default:
                    break;
            }
            String msg = "[§dLC-AutoMove§f]: §6Changed direction to §a§l" + autoMove.settings.autoMoveDirection;
            sender.addChatMessage(new ChatComponentText(msg));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;  // 権限レベル0（すべてのプレイヤーが使用可能）
    }
}
