package com.lostaris.bukkit.MineralScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * MineralDetector for Bukkit
 *
 * @author Lostaris
 */
public class MineralScanner extends JavaPlugin {
    private final MineralScannerPlayerListener playerListener = new MineralScannerPlayerListener(this);
    //private final MineralDetectorBlockListener blockListener = new MineralDetectorBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    public MineralScanner(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        // TODO: Place any custom initialisation code here

        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }   

    //This is used to enable the plugin via a plugin manager.
    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_ITEM_HELD , playerListener, Priority.Monitor, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE , playerListener, Priority.Monitor, this);

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("Goodbye world!");
    }
    
    public boolean onCommand(org.bukkit.command.CommandSender sender,
			org.bukkit.command.Command command, String commandLabel, String[] args) {
    	Player player = null;
		// if the command sender is a player
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		playerListener.setPlayer(player);
		//String[] split = args;
		String commandName = command.getName().toLowerCase();
		if (commandName.equals("scan")) {
			ArrayList<Block> blocks = playerListener.scanArea();
			playerListener.printScanArea(blocks);
		}
    
    	return false;
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

