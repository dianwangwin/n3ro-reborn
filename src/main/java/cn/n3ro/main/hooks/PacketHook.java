package cn.n3ro.main.hooks;

import cn.n3ro.main.Client;
import cn.n3ro.main.events.EventPacket;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class PacketHook implements Opcodes {

    public static void transformNetworkManager(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("scheduleOutboundPacket") || methodNode.name.equalsIgnoreCase("func_150725_a")){
            final InsnList preInsn = new InsnList();
            preInsn.add(new VarInsnNode(Opcodes.ALOAD, 1));//
            preInsn.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/darkmagician6/eventapi/types/EventType", "SEND", "Lcom/darkmagician6/eventapi/types/EventType;"));
            preInsn.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(PacketHook.class), "channelRead0Hook","(Ljava/lang/Object;Lcom/darkmagician6/eventapi/types/EventType;)Z", false));
            final LabelNode jmp = new LabelNode();
            preInsn.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            preInsn.add(new InsnNode(Opcodes.RETURN));
            preInsn.add(jmp);
            preInsn.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            methodNode.instructions.insert(preInsn);
        }
    }

    public static boolean channelRead0Hook(Object packet, EventType eventType) {
        if(packet != null) {
            if (packet instanceof S05PacketSpawnPosition){
                Client.canCancle = false;
            }
            final EventPacket event = new EventPacket(eventType,packet);
            EventManager.call(event);
            if (event.getPacket() instanceof S08PacketPlayerPosLook){
                Client.canCancle = true;
            }
            return event.isCancelled();
        }
        return false;
    }
}
