package cn.n3ro.main.module.modules.RENDER;

import cn.n3ro.main.events.EventRenderPlayer;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import com.darkmagician6.eventapi.EventTarget;

public class Chams extends Module {
    public Chams() {
        super("Chams", Category.RENDER);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onrenderplayer(EventRenderPlayer e){
//        if (e.getType() == EventType.PRE){
//            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
//            GL11.glPolygonOffset(1.0F, -2000000F);
//        }else if (e.getType() == EventType.POST){
//            GL11.glPolygonOffset(1.0F, 2000000F);
//            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
//        }
    }
}
