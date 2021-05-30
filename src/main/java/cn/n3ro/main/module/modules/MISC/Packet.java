package cn.n3ro.main.module.modules.MISC;

import cn.n3ro.main.events.EventPacket;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;


public class Packet extends Module {

    private int sb;

    public Packet() {
        super("Packet", Category.MOVEMENT);
        set(true);
    }


    @Override
    public void onEnable() {
        super.onEnable();
        sb = 0;
    }


    @EventTarget
    public void onUpdate(EventPacket event) {
        if (mc.thePlayer.ticksExisted <= 1) sb = 0;

        if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            C0FPacketConfirmTransaction c0FPacketConfirmTransaction = (C0FPacketConfirmTransaction) event.getPacket();
            sb++;

            if (sb > 7 && ((C0FPacketConfirmTransaction) c0FPacketConfirmTransaction).getUid() < 0 && ((C0FPacketConfirmTransaction) c0FPacketConfirmTransaction).getWindowId() == 0) {
                System.out.println("Disabled");
                event.setCancelled(true);
            }
        }
    }


}
