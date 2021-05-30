package cn.n3ro.main.hooks;

import cn.n3ro.main.Client;
import cn.n3ro.main.ClientLoader;
import cn.n3ro.main.events.EventTick;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.module.modules.COMBAT.Reach;
import cn.n3ro.main.module.modules.RENDER.NoHurtCam;
import cn.n3ro.main.utils.ASMUtil;
import cn.n3ro.main.utils.ClassNodeUtils;
import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.*;
import java.util.Objects;

public class MinecraftHook {

    public static void transformMinecraft(ClassNode clazz, MethodNode method) {
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.name.equals("func_71407_l") || methodInsn.name.equals("runTick")) {
                    method.instructions.insert(insn, new MethodInsnNode(Opcodes.INVOKESTATIC,//opcodes invoke static method
                            Type.getInternalName(MinecraftHook.class), //method ownner
                            "hookRunTick", //method name
                            "()V", //method desc
                            false));
                }
                if (methodInsn.name.equals("dispatchKeypresses") | method.name.equalsIgnoreCase("func_152348_aa")) {
                    method.instructions.insert(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftHook.class), "hookKeyHandler", "()V", false));
                }
              
            }
        }
    }


    
    public static float getCollisionBorderSize_Size()
    {
    	if(ModuleManager.getModuleByName("Reach").isEnable() /*&& !ModManager.getModule("TPHit").isEnabled()*/) {
    		System.out.println("reach is ok");
		return Reach.reach.getValue().floatValue();
	} else {
		
		return Minecraft.getMinecraft().playerController.isInCreativeMode() ? 5F :4.5F;
	}
    }
 
    
    /**
     * ghost client start method
     */
    public static void hookRunTick() {
        if (!Client.initiated) {
            new Client();
        }
        EventManager.call(new EventTick());
    }

    /**
     * KeyHandler method
     */
    public static void hookKeyHandler() {
        if (Keyboard.getEventKeyState()) {
            for (Module mod : ModuleManager.getModList()) {
                if (Minecraft.getMinecraft().currentScreen == null) {
                    if (mod.getKey() != (Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()))
                        continue;
                    mod.set(!mod.isEnable());
                }
                break;
            }
        }
    }

    
  
    


  




  //  public float getBlockReachDistance() {
//    	if(ModuleManager.getModuleByName("Reach").isEnable() /*&& !ModManager.getModule("TPHit").isEnabled()*/) {
//    		return (float) Reach.getReach() + 1.5f;
//    	} else {
//    		return 4.5F;
//    	}
//    	return 10F;
//	}
//    public static void getBlockReachDistance() {
//    	System.out.println("asd111");
//    }
    public static void chamsHook1(Object object){
        if (Client.instance.moduleManager.getModuleByName("Chams").isEnable() && object instanceof EntityPlayer){
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
        }
    }
    public static void chamsHook2(Object object){
        if (Client.instance.moduleManager.getModuleByName("Chams").isEnable() && object instanceof EntityPlayer){
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}
