package cn.n3ro.main.module.modules.RENDER;

import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;

public class ViewClip extends Module {
    public ViewClip() {
        super("ViewClip", Category.RENDER);
    }

    public static boolean x = false;

    @Override
    public void onEnable() {
        x = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        x = false;
        super.onDisable();
    }
}
