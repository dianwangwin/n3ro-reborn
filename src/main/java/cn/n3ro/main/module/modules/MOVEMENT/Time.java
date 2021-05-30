package cn.n3ro.main.module.modules.MOVEMENT;

import cn.n3ro.main.events.EventTick;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.utils.JReflectUtility;
import cn.n3ro.main.value.Numbers;

import com.darkmagician6.eventapi.EventTarget;

public class Time extends Module {
	public static Numbers<Double> timerspeeds = new Numbers<Double>("TimeSpeed", 1D, 0.1D, 10D, 0.1D);
    public Time() {
        super("Timer", Category.MOVEMENT);
        this.addValues(timerspeeds);
    }
    
    @EventTarget
    private void onUpdate(EventTick event) {
         JReflectUtility.timer().timerSpeed = timerspeeds.getValue().floatValue();
    }
   @Override
    public void onEnable() {
	   JReflectUtility.timer().timerSpeed = timerspeeds.getValue().floatValue();
    }
    @Override
    public void onDisable() {
    	 JReflectUtility.timer().timerSpeed = 1F;
    }

}
