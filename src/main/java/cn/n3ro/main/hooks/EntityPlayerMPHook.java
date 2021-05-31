package cn.n3ro.main.hooks;

import cn.n3ro.main.Client;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.modules.COMBAT.Reach;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class EntityPlayerMPHook implements Opcodes{

    /*
     *
     *	just reach test
     */
    public static void transformRange(ClassNode clazz, MethodNode method) {
        if (method.name.equalsIgnoreCase("getBlockReachDistance") || method.name.equalsIgnoreCase("func_78757_d")){
            InsnList insnList = new InsnList();
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(EntityPlayerMPHook.class), "getBlockReachDistance", "()F", false));
            method.instructions.insert(insnList);
        }
    }

    /*
     *
     *	just reach test
     */

    public static float getBlockReachDistance(){
        if(ModuleManager.getModule(Reach.class).isEnable()) {
            return Reach.reach.getValue().floatValue() ;
        } else {
            return Minecraft.getMinecraft().playerController.isInCreativeMode() ? 5F :4.5F;
        }
    }
}
