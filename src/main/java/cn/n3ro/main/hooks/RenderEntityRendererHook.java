package cn.n3ro.main.hooks;

import cn.n3ro.main.ClientLoader;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.modules.RENDER.NoHurtCam;
import cn.n3ro.main.utils.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Objects;

public class RenderEntityRendererHook implements Opcodes  {

    public static void transformRenderEntityRenderer(ClassNode classNode, MethodNode method) {
        if (method.name.equalsIgnoreCase("hurtCameraEffect") || method.name.equalsIgnoreCase("func_78482_e")){
            InsnList insnList = new InsnList();
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftHook.class), "isNohurtcamEnable", "()Z", false));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));
            insnList.add(new InsnNode(Opcodes.RETURN));
            insnList.add(labelNode);
            method.instructions.insert(insnList);
        }
        if ((method.name.equalsIgnoreCase("orientCamera") || method.name.equalsIgnoreCase("func_78467_g")) && method.desc.equalsIgnoreCase("(F)V")){
            AbstractInsnNode target = ASMUtil.findMethodInsn(method, Opcodes.INVOKEVIRTUAL,"net/minecraft/util/Vec3",  ClientLoader.runtimeDeobfuscationEnabled ?"func_72438_d" : "distanceTo","(Lnet/minecraft/util/Vec3;)D");
            if (target != null){
                InsnList insnList2 = new InsnList();

                InsnList insnList = new InsnList();
                insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(MinecraftHook.class),"isViewClipEnabled","()Z",false));
                LabelNode labelNode = new LabelNode();
                insnList.add(new JumpInsnNode(Opcodes.IFNE,labelNode));
                method.instructions.insertBefore(ASMUtil.forward(target,8),insnList);
                insnList2.add(labelNode);
                insnList2.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
                method.instructions.insert(ASMUtil.forward(target,13),insnList2);
            }
        }



    }

    public static boolean isViewClipEnabled() {
        return Objects.requireNonNull(ModuleManager.getModuleByName("ViewClip")).isEnable();
    }

    public static boolean isNohurtcamEnable(){
        return NoHurtCam.no;
    }
}
