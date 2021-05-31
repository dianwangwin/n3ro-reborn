package cn.n3ro;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import cn.n3ro.main.ClientLoader;
import cn.n3ro.main.hooks.*;
import cn.n3ro.main.utils.LoggerUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;


import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer, ClassFileTransformer, Opcodes {

    public static Set<String> classNameSet;

    static {
        classNameSet = new HashSet<String>();
        String[] nameArray = new String[]{
                "net.minecraft.client.Minecraft",
                "net.minecraft.entity.Entity",
                "net.minecraft.client.entity.EntityPlayerSP",
                "net.minecraft.profiler.Profiler",
                "net.minecraft.network.NetworkManager",
                "net.minecraft.entity.EntityLivingBase",
                "net.minecraft.client.renderer.EntityRenderer",
                "net.minecraft.client.renderer.entity.RenderPlayer",
                "net.minecraft.client.multiplayer.PlayerControllerMP"
        };
        for (int i = 0; i < nameArray.length; i++) {
            classNameSet.add(nameArray[i]);
        }
    }

    public static boolean needTransform(String name) {
        return classNameSet.contains(name);
    }


    @Override
    public byte[] transform(String name, String transformedName, byte[] classByte) {
        return transform(transformedName, classByte);
    }

    private byte[] transformMethods(byte[] bytes, BiConsumer<ClassNode, MethodNode> transformer) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        ClassWriter classWriter = new ClassWriter(0);
        try {
            classReader.accept(classNode, 0);
            classNode.methods.forEach(m ->
                    transformer.accept(classNode, m)
            );
            LoggerUtils.info("正在转换 -> " + classNode.name);
            LoggerUtils.info("具体转换 -> " + classReader.getClassName());
            classNode.accept(classWriter);
        } catch (Throwable e){
            LoggerUtils.info("异常 -> " + classReader);
        }
        return classWriter.toByteArray();
    }


    @SuppressWarnings("deprecation")
	public byte[] transform(String name, byte[] classByte) {

        try {
            switch (name) {
                case "net.minecraft.client.Minecraft":
                    return transformMethods(classByte, MinecraftHook::transformMinecraft);
                case "net.minecraft.entity.Entity":  // HitBox and EntityMove
                    return transformMethods(classByte, EntityHook::transformEntity);
                case "net.minecraft.client.entity.EntityPlayerSP": // SP
                    return transformMethods(classByte, EntityPlayerSPHook::transformEntityPlayerSP);
                case "net.minecraft.client.multiplayer.PlayerControllerMP": // MP
                    return transformMethods(classByte, EntityPlayerMPHook::transformRange);
                case "net.minecraft.profiler.Profiler":
                    return transformMethods(classByte, ProfilerHook::transformProfiler);//	2D3D-Render
                case "net.minecraft.network.NetworkManager":  //	EventPacket
                    return transformMethods(classByte, PacketHook::transformNetworkManager);
                case "net.minecraft.client.renderer.EntityRenderer": //ViewClip Reach
                    return transformMethods(classByte, RenderEntityRendererHook::transformRenderEntityRenderer);
                case "net.minecraft.client.renderer.entity.RenderPlayer":  //	RenderPlayer
                    return transformMethods(classByte, RendererLivingEntityHook::transformRendererLivingEntity);
            }

        } catch (Exception e) {
            LogManager.getLogger().log(Level.ERROR, ExceptionUtils.getStackTrace(e));
        }

        return classByte;
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
	
	public static MethodNode getDeclaredMethod(ClassNode cn, String... name) {
		List<MethodNode> list = cn.methods;
		for (int j=0; j<list.size(); j++) {
			MethodNode mn = list.get(j);
			for (int i = 0; i<name.length; i++) {
				if (name[i].equals(mn.name))
					return mn;
			}
		}
		return null;
	}

    @Override
    public byte[] transform(ClassLoader arg0, String name, Class<?> clazz, ProtectionDomain arg3, byte[] classByte)
            throws IllegalClassFormatException {
        ClientLoader.runtimeDeobfuscationEnabled = true;
        return transform(clazz.getName(), classByte);
    }

}
