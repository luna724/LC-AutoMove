package iloveichika.luna724.lc_automove;

import iloveichika.luna724.lunaapi.LunaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class RotationManager {
    private float targetYaw;
    private float startYaw;
    private int ticksRemaining;
    private boolean enableYawChanger;

    private final ChatLib chatLib;
    public RotationManager(ChatLib chatLib) {
        this.chatLib = chatLib;
    }

    /*
    プレイヤーの Yaw をいじるクラス
    setTargetYaw を呼び出すことで変更を開始できる
     */

    public void startYawChanger(float targetYaw, int ticksTaken) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player != null) {
            // Yaw を修正
            float Yaw;
            if (targetYaw < -180) {
                Yaw = -180;
            } else if (targetYaw > 180) {
                Yaw = 180;
            } else {
                Yaw = targetYaw;
            }

            chatLib.sendStatusInfo("[YawChanger]: Changing Yaw to " + (Yaw - 180));
            this.enableYawChanger = true;
            this.startYaw = player.rotationYaw;
            this.targetYaw = Yaw;
            this.ticksRemaining = ticksTaken;
        }
    }

    private void endsYawChanger() {
        chatLib.sendStatusInfo("[YawChanger]: Changed Yaw to " + (this.targetYaw));
        this.enableYawChanger = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!enableYawChanger) { return; }
        if (ticksRemaining > 0) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                float yawStep = targetYaw - startYaw;

				// 差分が180度を超えている場合、逆回転するようにする
				if (yawStep > 180) {
					yawStep -= 360;
				} else if (yawStep < -180) {
					yawStep += 360;
				}

				// yawStep の差分 / ticks を行い、少しづつ変更する
				player.rotationYaw = startYaw + (yawStep / ticksRemaining);
				ticksRemaining--;
            }
        }
        else {
            endsYawChanger();
        }
    }
}