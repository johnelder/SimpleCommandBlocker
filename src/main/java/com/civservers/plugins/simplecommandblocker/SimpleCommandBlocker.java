package com.civservers.plugins.simplecommandblocker;




import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;


public final class SimpleCommandBlocker extends JavaPlugin {

	private String mcVer = Bukkit.getVersion();
    private Map<String, Object> msgs = getConfig().getConfigurationSection("messages").getValues(true);
	
	
	@Override
    public void onEnable() {
		getConfig().options().copyDefaults(true);
	    saveConfig();
	    reload();
	    Utilities.init(this);
	    
		
		boolean keepLoading = true;
		if (mcVer.contains("MC: 1.13")) {
			Utilities.debug("Loading files for version 1.13.1");
			Bukkit.getServer().getPluginManager().registerEvents(new Listeners1131(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new Listeners(this), this);
		} else if (mcVer.contains("MC: 1.12")) {
			Utilities.debug("Loading files for version 1.12.2");
			Bukkit.getServer().getPluginManager().registerEvents(new Listeners(this), this);
		} else if (mcVer.contains("MC: 1.8")) {
			Utilities.debug("Loading files for version 1.8.8");
			Bukkit.getServer().getPluginManager().registerEvents(new Listeners(this), this);
		} else {
			Utilities.debug("No matching version found.");
			Utilities.sendConsole(ChatColor.RED + "DISABLED: Server version not supported.");
			keepLoading = false;
		}
		
		if (keepLoading) {
		    
			this.getCommand("simplecommandblocker").setExecutor(new PluginCommandExecutor(this));
			
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
		}
    }
   
    public boolean reload() {
		reloadConfig();
		msgs = getConfig().getConfigurationSection("messages").getValues(true);
		return true;     
    }

    public String getMinecraftVersion() {
        return mcVer;
    }

    public Map<String, Object> getMessages() {
        return msgs;
    }
}

