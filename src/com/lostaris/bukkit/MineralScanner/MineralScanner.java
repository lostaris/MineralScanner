package com.lostaris.bukkit.MineralScanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
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
	private HashMap<String, String> settings;
	private ArrayList<Integer> blocks;
	public static final Logger log = Logger.getLogger("Minecraft");

	/*public MineralScanner(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        // TODO: Place any custom initialisation code here

        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }*/

	//This is used to enable the plugin via a plugin manager.
	public void onEnable() {
		// TODO: Place any custom enable code here including the registration of any events

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_ITEM , playerListener, Priority.Monitor, this);
		//pm.registerEvent(Event.Type.PLAYER_MOVE , playerListener, Priority.Monitor, this);

		// EXAMPLE: Custom code, here we just output some info so we can check all is well
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		settings = readConfig();
		setBlocks();
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
		String[] split = args;
		String commandName = command.getName().toLowerCase();
		if (commandName.equals("scan")) {
			//ArrayList<Block> blocks = playerListener.scanArea();
			//playerListener.printScanArea(blocks);
			if (split.length == 0) {
				playerListener.getCone();
			} else if (split[0].equalsIgnoreCase("reload")) {
				player.sendMessage("§3Re-loaded MineralScanner config");
				settings = readConfig();
				setBlocks();
			}
		}
		return true;
	}

	public HashMap<String, String> readConfig() {
		String fileName = "plugins/MineralScanner/Config.properties";
		HashMap<String, String> settings = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			String setting = null;
			String value;
			while ((line = reader.readLine()) != null) {
				if ((line.trim().length() == 0) || 
						(line.charAt(0) == '#')) {
					continue;
				}
				int keyPosition = line.indexOf('=');
				setting = line.substring(0, keyPosition).trim();
				value = line.substring(keyPosition +1, line.length());
				settings.put(setting, value);
			}			
		}catch (Exception e) {
			log.warning("Error reading MineralScanner config");
		}		
		return settings;

	}

	public HashMap<String, String> getConfig() {
		return settings;
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

	public void setBlocks() {
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		String[] blockString = getConfig().get("blocks").split(",");
		for (String i : blockString) {
			blocks.add(Integer.valueOf(i));
		}
		this.blocks = blocks;
	}

	public ArrayList<Integer> getBlocks() {
		return blocks;
	}
}

