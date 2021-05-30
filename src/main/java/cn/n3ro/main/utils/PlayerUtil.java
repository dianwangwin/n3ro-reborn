package cn.n3ro.main.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class PlayerUtil {
    private static Minecraft mc;

    static {
        PlayerUtil.mc = Minecraft.getMinecraft();
    }

    public static boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }

    public static void debug(Object string) {
        if (string != null && mc.thePlayer != null )
            mc.thePlayer.addChatMessage(new ChatComponentText("§c[DEBUG] §r " + string));
    }
}
