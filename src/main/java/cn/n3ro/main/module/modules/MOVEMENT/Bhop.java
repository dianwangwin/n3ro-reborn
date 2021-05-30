package cn.n3ro.main.module.modules.MOVEMENT;

import cn.n3ro.main.events.EventMove;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.utils.PlayerUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

public class Bhop extends Module {
    public Bhop() {
        super("Bhop", Category.MOVEMENT);
        setKey(Keyboard.KEY_Z);
    }


    @EventTarget
    public void onPre(EventMove e) {
    	if (e.getEntity() != mc.thePlayer){
            return;
        }
    	  mc.thePlayer.jump();
        if (mc.thePlayer.isSneaking() || mc.thePlayer.isInWater() || mc.gameSettings.keyBindJump.isPressed() || mc.gameSettings.keyBindBack.isPressed()) {
            return;
        }
       
        if (PlayerUtil.isMoving()) {
            if (mc.thePlayer.onGround) {
            	if (e.getEntity() == mc.thePlayer){
                 
                }
                mc.thePlayer.setSprinting(true);
            } else {
            	System.out.println("no");
                getStrafe(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ));
                mc.thePlayer.setSprinting(true);
            }
        }
    }


    private void getStrafe(double speed) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double yaw = player.rotationYaw;
        boolean isMoving = player.moveForward != 0 || player.moveStrafing != 0;
        boolean isMovingForward = player.moveForward > 0;
        boolean isMovingBackward = player.moveForward < 0;
        boolean isMovingRight = player.moveStrafing > 0;
        boolean isMovingLeft = player.moveStrafing < 0;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean isMovingStraight = isMovingForward || isMovingBackward;

        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45;
            } else if (isMovingForward) {
                yaw -= 45;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135;
            } else if (isMovingBackward) {
                yaw -= 135;
            }

            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }
}
