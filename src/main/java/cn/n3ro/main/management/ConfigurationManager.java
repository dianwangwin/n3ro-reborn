package cn.n3ro.main.management;

import cn.n3ro.main.management.FileAndConfig.KeybindConfiguration;
import cn.n3ro.main.management.FileAndConfig.ModuleStateConfiguration;
import cn.n3ro.main.management.FileAndConfig.ModuleValues;

public class ConfigurationManager
{
    private static volatile ConfigurationManager INSTANCE = new ConfigurationManager();

    public ConfigurationManager()
    {
       
        new KeybindConfiguration();
        new ModuleStateConfiguration();
        new ModuleValues();
       
    }

    public static ConfigurationManager instance()
    {
        return INSTANCE;
    }
}
