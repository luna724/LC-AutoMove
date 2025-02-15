package luna724.iloveichika.automove

import luna724.iloveichika.automove.AutoMoveMod.Companion.HEADER
import luna724.iloveichika.automove.AutoMoveMod.Companion.mc
import net.minecraft.util.ChatComponentText

class _ChatLib {
    fun send(message: String) {
        mc.thePlayer?.addChatMessage(
            ChatComponentText(HEADER + message)
        ) ?: println(message)
        return
    }

    fun command(message: String) {
        mc.thePlayer?.sendChatMessage("/${message}") ?:
        return
    }
}