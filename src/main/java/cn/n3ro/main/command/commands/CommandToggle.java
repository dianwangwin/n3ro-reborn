package cn.n3ro.main.command.commands;

import cn.n3ro.main.command.Command;
import cn.n3ro.main.management.ModuleManager;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.utils.ClientUtil;

public class CommandToggle extends Command {

    public CommandToggle(String[] commands) {
        super(commands);
        this.setArgs(".toggle <module>");
    }

    @Override
    public void onCmd(String[] args) {
        if(args.length < 2) {
            ClientUtil.sendClientMessage(this.getArgs());
        } else {
            String mod = args[1];
            for (Module m : ModuleManager.getModList()) {
                if(m.getName().equalsIgnoreCase(mod)) {
                    m.set(!m.isEnable());
                    ClientUtil.sendClientMessage( m.getName() + " " + (!m.isEnable() ? "was \u00a7cdisabled" : "was \u00a7aenabled"));
                    return;
                }
            }
        }
    }
}
