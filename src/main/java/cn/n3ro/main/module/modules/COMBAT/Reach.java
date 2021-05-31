package cn.n3ro.main.module.modules.COMBAT;

import cn.n3ro.main.events.EventUpdate;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.value.Numbers;

import com.darkmagician6.eventapi.EventTarget;

public class Reach extends Module {

	public static Numbers<Double> reach = new Numbers<Double>("Reach_Reach", 3.0d, 3.0d, 6.0d, 0.1d);

    public Reach() {
        super("Reach", Category.COMBAT);
        this.addValues(reach);
    }
    
    @EventTarget
    private void onUpdate(EventUpdate event) {
    
    	
    }
    
    @Override
    public void onDisable() {
    	
    }

    public static double getReach() {
		return ModuleManager.getModuleByName("Reach").isEnable() ? reach.getValue().floatValue() : 3.0f;
	}

	
}
