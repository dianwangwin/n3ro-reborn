package cn.n3ro.main.hooks;

import cn.n3ro.main.Client;
import cn.n3ro.main.command.Command;
import cn.n3ro.main.events.EventMotion;
import cn.n3ro.main.events.EventMove;
import cn.n3ro.main.events.EventUpdate;
import cn.n3ro.main.management.CommandManager;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.modules.COMBAT.Reach;
import cn.n3ro.main.utils.ASMUtil;
import javassist.CtClass;
import javassist.CtMethod;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.Minecraft;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 *
 * addChatMessage func_145747_a
 *
 *
 */
public class EntityPlayerSPHook implements Opcodes{

    public static void transformEntityPlayerSP(ClassNode classNode, MethodNode method) {
        if (method.name.equalsIgnoreCase("func_70071_h_") | method.name.equalsIgnoreCase("onUpdate")){
            method.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(EntityPlayerSPHook.class),"hookOnUpdate","()V",false));
        }
        if (method.name.equalsIgnoreCase("func_175161_p") | method.name.equalsIgnoreCase("onUpdateWalkingPlayer")){
            InsnList preInsn = new InsnList();
            preInsn.add(new FieldInsnNode(GETSTATIC, "com/darkmagician6/eventapi/types/EventType", "PRE", "Lcom/darkmagician6/eventapi/types/EventType;"));
            preInsn.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(EntityPlayerSPHook.class), "hookMotionUpdate","(Lcom/darkmagician6/eventapi/types/EventType;)V", false));
            method.instructions.insert(preInsn);

            InsnList postInsn = new InsnList();
            postInsn.add(new FieldInsnNode(GETSTATIC, "com/darkmagician6/eventapi/types/EventType", "POST", "Lcom/darkmagician6/eventapi/types/EventType;"));
            postInsn.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(EntityPlayerSPHook.class), "hookMotionUpdate","(Lcom/darkmagician6/eventapi/types/EventType;)V", false));
            method.instructions.insertBefore(ASMUtil.bottom(method), postInsn);


            // replace field
            for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()){
                if (abstractInsnNode.getOpcode() == ALOAD &
                        abstractInsnNode.getNext() instanceof FieldInsnNode
                ){
                    if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(Client.runtimeobfuscationEnabled ? "field_70163_u" : "posY")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC, Type.getInternalName(EventMotion.class),
                                        "y","D"));
                        method.instructions.remove(abstractInsnNode);
                    }else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(Client.runtimeobfuscationEnabled ? "field_70177_z" : "rotationYaw")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,Type.getInternalName(EventMotion.class),
                                        "yaw","F"));
                        method.instructions.remove(abstractInsnNode);
                    }else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(Client.runtimeobfuscationEnabled ? "field_70125_A" : "rotationPitch")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,Type.getInternalName(EventMotion.class),
                                        "pitch","F"));
                        method.instructions.remove(abstractInsnNode);
                    } else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(Client.runtimeobfuscationEnabled ? "field_70122_E" : "onGround")
					){
						method.instructions.set(abstractInsnNode.getNext(),
								new FieldInsnNode(GETSTATIC,Type.getInternalName(EventMotion.class),
										"onGround","Z"));
						method.instructions.remove(abstractInsnNode);
					}
                }
            }
        }

        if (method.name.equalsIgnoreCase("func_71165_d") || method.name.equalsIgnoreCase("sendChatMessage")) {

            final InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(EntityPlayerSPHook.class), "command", "(Ljava/lang/String;)V", false));

            insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnList.add(new LdcInsnNode("."));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(EntityPlayerSPHook.class), "isNoCommandEnabled", "(Ljava/lang/String;Ljava/lang/String;)Z", false));

            final LabelNode jmp = new LabelNode();
            insnList.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            insnList.add(new InsnNode(Opcodes.RETURN));
            insnList.add(jmp);
            insnList.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            method.instructions.insert(insnList);
        }
    }




    public static void hookMotionUpdate(EventType stage) {
        if (stage == EventType.PRE){
            EventMotion em = new EventMotion(Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.rotationYaw,Minecraft.getMinecraft().thePlayer.rotationPitch,Minecraft.getMinecraft().thePlayer.onGround);
            EventManager.call(em);
        }else if (stage == EventType.POST){
            EventMotion ep = new EventMotion(stage);
            EventManager.call(ep);
        }

    }
    public static CtMethod getDeclaredMethod(CtClass cc, String... name) {
		CtMethod[] cms = cc.getMethods();
		for (int j = 0; j<cms.length; j++) {
			CtMethod cm = cms[j];
			for (int i = 0; i<name.length; i++) {
				if (name[i].equals(cm.getName()))
					return cm;
			}
		}

		return null;
	}
    public static void hookOnUpdate(){
        EventManager.call(new EventUpdate());
    }

    public static void command(String message){
        String s = CommandManager.removeSpaces(message);
        if (message.startsWith(".")/* && !NoCommand.n*/) {
            for (Command cmd : CommandManager.getCommands()) {
                int i = 0;
                while (i < cmd.getCommands().length) {
                    if (s.split(" ")[0].equals("." + cmd.getCommands()[i])) {
                        cmd.onCmd(s.split(" "));
                        return;
                    }
                    ++i;
                }
            }
            return;
        }
    }
    public static boolean isNoCommandEnabled(String s,String s1) {
        return s.startsWith(s1)/* && !NoCommand.n*/;
    }

}
