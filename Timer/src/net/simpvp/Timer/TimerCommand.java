package net.simpvp.Timer;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.ChatColor;


public class TimerCommand implements CommandExecutor {

	private static Plugin plugin;
	public TimerCommand(Plugin plugin) {
		TimerCommand.plugin = plugin;
	}
	
	// /timer <seconds> <~x> <~y> <~z>
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {

		Location location = null;
		
		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "Command can only be run from a command block");
			return true;
		}
	
		if (sender instanceof BlockCommandSender) {
			location = ((BlockCommandSender) sender).getBlock().getLocation();
		}
		
		if (args.length != 4) {
			messageNearestOp(location,"/timer <seconds> <~x> <~y> <~z>");
			return false;
		}
		try {
			int seconds = Integer.valueOf(args[0]);
			int x = Integer.valueOf(args[1]);
			int y = Integer.valueOf(args[2]);
			int z = Integer.valueOf(args[3]);
			startTimer(seconds,location,x,y,z);
		} catch (Exception e) {
			messageNearestOp(location,"/timer <seconds> <~x> <~y> <~z>");
			return false;
		}
        return false;
    }
	
	//Start timer
	public void startTimer(int time, Location location, int x, int y, int z) {
		
		//Beginning of timer
		messageNearestOp(location,"Starting timer of " + (time) + " seconds");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			//End of timer
			@Override
			public void run() {
				if (location != null) {
					Location newLocation = location.add(x, y, z);
					setType(newLocation.getBlock());
				}
			}};
		timer.schedule(task,time*1000);  
	}
	
	//Block needs to be set using a bukkit runnable, otherwise you get an asynchronous error
	public static void setType(final Block block){
	    new BukkitRunnable() {
	        public void run() {
	            block.setType(Material.REDSTONE_BLOCK);
	        }
	    }.runTask(plugin);
	}

	//Message OPs in creative mode within 10 blocks of the command block
	public void messageNearestOp(Location loc, String msg) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.isOp() && player.getGameMode() == GameMode.CREATIVE) {
				if (player.getLocation().distance(loc) <= 10) {
					player.sendMessage(ChatColor.RED + msg);
				}
			}
		}
	}

}


