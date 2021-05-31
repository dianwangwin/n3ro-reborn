package cn.n3ro;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Loader {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            if (!checkOs()) {
                JOptionPane.showMessageDialog(dialog, "Some error", "Rose", 1);
                return;
            }

            (new Loader()).inject();
            exit();
        } catch (Exception var2) {
            exit();
        }

    }

    private static boolean checkOs() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows") && System.getProperty("os.arch").contains("64");
    }

    private static String encrypt(String s) {
        StringBuilder encrypt = new StringBuilder();

        byte[] var5;
        int var4 = (var5 = s.getBytes(StandardCharsets.UTF_8)).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            encrypt.append((b + "%"));
        }

        encrypt.reverse();
        return encrypt.toString().replace("0", "X").replace("1", "Y");
    }

    private static void exit() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + ManagementFactory.getRuntimeMXBean().getName().substring(0, ManagementFactory.getRuntimeMXBean().getName().indexOf("@")));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

        System.exit(0);
    }

    private void inject() {
        File dllFile = new File(((System.getProperty("user.dir"))) + "\\attach.dll");
        if (!dllFile.exists()) {
            this.error(((dllFile.getAbsolutePath())) + "找不到 attach.dll");
        } else {
            this.initDll(dllFile);
            VirtualMachineDescriptor attach_ = VirtualMachine.list().stream().filter((m) -> {
                return m.displayName().startsWith("net.minecraft.launchwrapper.Launch");
            }).findFirst().orElse(null);
            if (attach_ == null) {
                this.error("找不到Minecraft进程");
            } else {
                try {
                    VirtualMachine attach = VirtualMachine.attach(attach_);
                    //File agentJar = File.createTempFile("+~" + (Math.random() > 0.5D ? "JF" : "DFE"), ".tmp");
                    File injectorJar = new File(Loader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    try {
                        attach.loadAgent(injectorJar.getAbsolutePath(), encrypt(injectorJar.getAbsolutePath()));
                    } catch (Exception var11) {
                        this.error(var11.getMessage());
                        exit();
                    }

                    attach.detach();
                    this.error("注入成功！。祝你好运！");
                    exit();
                } catch (Exception var12) {
                    exit();
                }
            }
        }
    }

    private void clearTemp(File folder, File x) {
        File[] var6;
        int var5 = (Objects.requireNonNull(var6 = folder.listFiles())).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            File f = var6[var4];
            if (!f.getName().equals(x.getName()) && f.getName().startsWith("+~") && !f.isDirectory() && !f.isHidden() && f.getName().toLowerCase().endsWith(".tmp")) {
                f.delete();
            }
        }

    }

    private void time(File file) {
        try {
            long time_milis = System.currentTimeMillis() - ((Math.random() > 0.5D ? TimeUnit.DAYS.toMillis(1L) : 0L) + TimeUnit.HOURS.toMillis((long)ThreadLocalRandom.current().nextInt(1, 23)) + TimeUnit.MINUTES.toMillis((long)ThreadLocalRandom.current().nextInt(1, 118)));
            BasicFileAttributeView attributes = (BasicFileAttributeView)Files.getFileAttributeView(Paths.get(file.getAbsolutePath()), BasicFileAttributeView.class);
            FileTime time = FileTime.fromMillis(time_milis);
            attributes.setTimes(time, time, time);
        } catch (Exception var6) {
        }

    }

    private void initDll(File dll) {
        try {
            if (System.getProperty("java.library.path") != null) {
                System.setProperty("java.library.path", ((dll.getAbsolutePath())) + System.getProperty("path.separator") + System.getProperty("java.library.path"));
            } else {
                System.setProperty("java.library.path", dll.getAbsolutePath());
            }

            Field declaredField = ClassLoader.class.getDeclaredField("sys_paths");
            declaredField.setAccessible(true);
            declaredField.set((Object)null, (Object)null);
            System.loadLibrary("attach");
        } catch (Exception var3) {
            exit();
        }

    }

    private static void copy(InputStream source, String destination) {
        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void error(String message) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, message, "N3RO", 1);
    }
}
 