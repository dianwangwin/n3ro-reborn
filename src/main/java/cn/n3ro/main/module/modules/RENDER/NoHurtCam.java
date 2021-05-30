package cn.n3ro.main.module.modules.RENDER;

import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;

public class NoHurtCam extends Module {
    public NoHurtCam() {
        super("NoHurtCam", Category.RENDER);
    }

    public static boolean no;

    @Override
    public void set(boolean state) {
        if (state){
            no = true;
        }else{
            no = false;
        }
        super.set(state);
    }
}
