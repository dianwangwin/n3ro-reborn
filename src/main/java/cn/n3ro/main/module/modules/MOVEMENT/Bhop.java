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
    }


    @EventTarget
    public void onMove(EventMove e) {
        if (e.getEntity() == mc.thePlayer){
           if (mc.thePlayer.onGround){
               mc.thePlayer.setPosition(mc.thePlayer.posX , mc.thePlayer.posY + 2 , mc.thePlayer.posZ);
           }
        }
    }

}
