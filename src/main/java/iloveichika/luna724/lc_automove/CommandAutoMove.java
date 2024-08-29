package iloveichika.luna724.lc_automove;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.List;

public class CommandAutoMove extends CommandBase {
    private static boolean containsOnlyAvailableChars(String input, List<String> availableChar) {
        for (char c : input.toLowerCase().toCharArray()) {
            if (!availableChar.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

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
                    "§6 - §f/automove setdirection <L/R/F/B>\n" +
                    "§e 歩く向きの設定 L = 左, R = 右, F = 前, B = 後\n" +
                    "§6 - §f/automove hoverclick\n" +
                    "§e 左クリックするかどうか";
            sender.addChatMessage(new ChatComponentText(helpMessage));
            return;
        }

        if ("start".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveEnabled = true;

            String msg = "[§dLC-AutoMove§f]: §6AutoMove Started.";
            sender.addChatMessage(new ChatComponentText(msg));

        } else if ("stop".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveEnabled = false;

            String msg = "[§dLC-AutoMove§f]: §6AutoMove Stopped.";
            autoMove.AutoMovingStop();
            sender.addChatMessage(new ChatComponentText(msg));

        } else if ("toggle".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveEnabled = !autoMove.settings.autoMoveEnabled;
            String msg;
            if (autoMove.settings.autoMoveEnabled) {
                msg = "[§dLC-AutoMove§f]: §6AutoMove Started.";
            } else {
                msg = "[§dLC-AutoMove§f]: §6AutoMove Stopped.";
                autoMove.AutoMovingStop();
            }
            sender.addChatMessage(new ChatComponentText(msg));

        } else if ("hoverclick".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveClickEnable = !autoMove.settings.autoMoveClickEnable;
            String msg;//      sender.addChatMessage(new ChatComponentText(msg));
            if (autoMove.settings.autoMoveClickEnable) {
                msg = "[§dLC-AutoMove§f]: §6hoverClick Enabled.";
            } else {
                msg = "[§dLC-AutoMove§f]: §6hoverClick Disabled.";
            }
            sender.addChatMessage(new ChatComponentText(msg));

        } else if ("setdirection".equalsIgnoreCase(args[0])) {
            /*
            /automove setdirection <l/r/f/b/..>
            /automove setdirection <l/r/f/b> 通常状態
            /automove setdirection lrf 左右前
            /automove setdirection リセット
            */

            if (args.length < 2) {
                autoMove.settings.autoMoveDirection = 0;
                String msg = "[§dLC-AutoMove§f]: §6Changed direction to §a§lNaN";
                sender.addChatMessage(new ChatComponentText(msg));
            }

            else {
                autoMove.settings.autoMoveDirection = 0;
                String key = args[1].toLowerCase();
                if (args[1].equalsIgnoreCase("reset")) {
                    String msg = "[§dLC-AutoMove§f]: §6Changed direction to §a§lNaN";
                    sender.addChatMessage(new ChatComponentText(msg));

                }

                List<String> availableChar = Arrays.asList("f", "b", "r", "l");
                if (!containsOnlyAvailableChars(key, availableChar)) {
                    String msg = "[§dLC-AutoMove§f]: §cUnknown args§f: " + key;
                    sender.addChatMessage(new ChatComponentText(msg));
                    return;
                }

                // あるならそれに変換する
                if (key.contains("l"))
                    autoMove.settings.autoMoveDirection |= autoMove.DIRECTION_LEFT;
                if (key.contains("r"))
                    autoMove.settings.autoMoveDirection |= autoMove.DIRECTION_RIGHT;
                if (key.contains("f"))
                    autoMove.settings.autoMoveDirection |= autoMove.DIRECTION_FORWARD;
                if (key.contains("b"))
                    autoMove.settings.autoMoveDirection |= autoMove.DIRECTION_BACKWARD;

                List<String> moveDirections = autoMove.getMovingKey();
                String msg = "[§dLC-AutoMove§f]: §6Changed direction to §a§l" + moveDirections.toString();
                sender.addChatMessage(new ChatComponentText(msg));
            }

        }
        else if ("safemode".equalsIgnoreCase(args[0])) {
            autoMove.settings.autoMoveStopWhenServerSwap = !autoMove.settings.autoMoveStopWhenServerSwap;

            String msg = "[§dLC-AutoMove§f]: §6Safemode: " + autoMove.settings.autoMoveStopWhenServerSwap;
            sender.addChatMessage(new ChatComponentText(msg));
        }

        else if (!(args.length < 2)) {
            if ("senddatatolunaclient".equalsIgnoreCase(args[1])) {
                String msg = autoMove.sendDataToLunaClient(args[0], args);
                sender.addChatMessage(new ChatComponentText(msg));

            }
        }

        autoMove.settings.saveConfig();
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            // 第一引数の補完を提供
            return getListOfStringsMatchingLastWord(args, "start", "stop", "toggle", "setdirection", "hoverclick", "safemode");
        }
        else if (args.length == 2) {
            // setdirection時に 第二引数の補完を提供
            if (args[0].equalsIgnoreCase("setdirection")) {
                return getListOfStringsMatchingLastWord(args, "reset", "l", "r", "f", "b", "rf", "lf", "br", "bf");
            }
            return getListOfStringsMatchingLastWord(args, "senddatatolunaclient");
        }
        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;  // 権限レベル0（すべてのプレイヤーが使用可能）
    }
}
