package cn.n3ro.main.hooks;

import cn.n3ro.main.utils.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class RendererLivingEntityHook {

    public static void transformRendererLivingEntity(ClassNode classNode, MethodNode method) {
        if (method.name.equalsIgnoreCase("doRender") || method.name.equalsIgnoreCase("func_76986_a")){
            InsnList insnList1 = new InsnList();
            InsnList insnList2 = new InsnList();
            insnList1.add(new VarInsnNode(Opcodes.ALOAD,1));
            insnList1.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftHook.class), "chamsHook1", "(Ljava/lang/Object;)V", false));
            method.instructions.insert(insnList1);

            insnList2.add(new VarInsnNode(Opcodes.ALOAD,1));
            insnList2.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftHook.class), "chamsHook2", "(Ljava/lang/Object;)V", false));
            method.instructions.insertBefore(ASMUtil.bottom(method),insnList2);
        }
    }
}
