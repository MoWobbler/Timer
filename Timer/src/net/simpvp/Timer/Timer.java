package net.simpvp.Timer;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;






public class Timer extends JavaPlugin implements Listener {

	public static Plugin plugin = null;
	
	@Override
	public void onEnable(){
		getCommand("timer").setExecutor(new TimerCommand(this));
		plugin = this;
		setPlugin(plugin);
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static void setPlugin(Plugin plug) {
		plugin = plug;
	}
	
}
