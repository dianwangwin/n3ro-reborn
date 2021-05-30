package cn.n3ro.main.command.commands;

import cn.n3ro.main.Client;
import cn.n3ro.main.command.Command;
import cn.n3ro.main.utils.ClientUtil;


public class CommandConfig extends Command {
    public CommandConfig(String[] command) {
        super(command);
        this.setArgs(".config <load/save> <config>");
    }

    @Override
    public void onCmd(String[] args) {
        System.out.println(args[0]);
        if(args.length != 3) {
            ClientUtil.sendClientMessage(this.getArgs());
        } else {
            if (args[1].equalsIgnoreCase("load")) {
                Client.instance.fileManager.loadConfig(args[2]);
            } else if (args[1].equalsIgnoreCase("save")) {
                Client.instance.fileManager.saveConfig(args[2]);
            } else {
                ClientUtil.sendClientMessage(this.getArgs());
            }
        }
    }
}
