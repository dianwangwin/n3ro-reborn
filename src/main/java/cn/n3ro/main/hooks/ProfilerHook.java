package cn.n3ro.main.hooks;

import cn.n3ro.main.events.EventRender2D;
import cn.n3ro.main.events.EventRender3D;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.utils.GLUProjection;
import com.darkmagician6.eventapi.EventManager;
import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class ProfilerHook implements Opcodes {


    public static void transformProfiler(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("startSection") || methodNode.name.equalsIgnoreCase("func_76320_a")){
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,1));
            insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ProfilerHook.class), "startSectionHook", "(Ljava/lang/String;)V", false));
            methodNode.instructions.insert(insnList);
        }
    }

    public static void startSectionHook(String info){
        if (info.equalsIgnoreCase("hand")){
            Event3D();
        }else if (info.equalsIgnoreCase("forgeHudText")){
            Event2D();
        }
    }
    public static void Event2D(){
        GlStateManager.pushMatrix();
        EventRender2D ed2 = new EventRender2D();
        EventManager.call(ed2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void Event3D(){
        GLUProjection projection = GLUProjection.getInstance();
        IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projectionPort);
        GL11.glGetInteger(2978, viewPort);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        projection.updateMatrices(viewPort, modelView, projectionPort, (double)scaledResolution.getScaledWidth() / (double)Minecraft.getMinecraft().displayWidth, (double)scaledResolution.getScaledHeight() / (double)Minecraft.getMinecraft().displayHeight);
        EventRender3D ed3 = new EventRender3D();
        EventManager.call(ed3);
    }

}
