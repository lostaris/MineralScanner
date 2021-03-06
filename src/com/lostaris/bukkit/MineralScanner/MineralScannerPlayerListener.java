package com.lostaris.bukkit.MineralScanner;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Lostaris
 */
public class MineralScannerPlayerListener extends PlayerListener {
	private final MineralScanner plugin;
	private Player player;
	public static final Logger log = Logger.getLogger("Minecraft");

	public MineralScannerPlayerListener(MineralScanner instance) {
		plugin = instance;
	}
	
	public void onPlayerItem(PlayerItemEvent event) {
		player = event.getPlayer();
		if (!plugin.isAllowed(player, "scan")) {
			return;
		}
		
		if (player.getItemInHand().getTypeId() != Integer.valueOf(getScanItem())) {
			return;
		} else {
			getCone();
		}
	}
	
	public String getScanItem() {
		String item = plugin.getConfig().get("scanItem");
		return item; 
	}
	
	//public 
	
	public Block blockFacing(String dir) {
		Location loc = player.getLocation();
		Block facing = player.getWorld().getBlockAt((int)Math.floor(loc.getX()),
				(int)Math.floor(loc.getY()), (int)Math.floor(loc.getZ())).getFace(BlockFace.valueOf(dir));
		return facing;	
	}
	
	public Block diagFacing(String dir1, String dir2) {
		Location loc = player.getLocation();
		Block facing = player.getWorld().getBlockAt((int)Math.floor(loc.getX()),
				(int)Math.floor(loc.getY()), (int)Math.floor(loc.getZ())).getFace(BlockFace.valueOf(dir1 + "_" + dir2));
		return facing;
	}

	public void getCone() {
		int r = (int)Math.abs((player.getLocation().getYaw() - 90.0F) % 360.0F);
		String dir;
		if (r < 23) {
			dir = "NORTH";
			//printCone(northSouth(dir));
			isMineral(northSouth(dir));
		} else {
			if (r < 68) {
				dir = "NORTH_EAST";
				//printCone(diagonal("NORTH", "EAST"));
				isMineral(diagonal("NORTH", "WEST"));
			} else {
				if (r < 113) {
					dir = "EAST";
					//printCone(eastWest(dir));
					isMineral(eastWest("WEST"));
				} else {
					if (r < 158) { 
						dir = "SOUTH_EAST";
						//printCone(diagonal("SOUTH", "EAST"));
						isMineral(diagonal("SOUTH", "WEST"));
					} else {
						if (r < 203) {
							dir = "SOUTH";
							//printCone(northSouth(dir));
							isMineral(northSouth(dir));
						} else {
							if (r < 248) {
								dir = "SOUTH_WEST";
								//printCone(diagonal("SOUTH", "WEST"));
								isMineral(diagonal("SOUTH", "EAST"));
							} else {
								if (r < 293) {
									dir = "WEST";
									//printCone(eastWest(dir));
									isMineral(eastWest("EAST"));
								}
								else {
									if (r < 338) {
										dir = "NORTH_WEST";
										//printCone(diagonal("NORTH", "WEST"));
										isMineral(diagonal("NORTH", "EAST"));
									} else {
										dir = "NORTH";
										//printCone(northSouth(dir));
										isMineral(northSouth(dir));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public ArrayList<Block> diagonal(String dir1, String dir2) {
		ArrayList<Block> scanCone = new ArrayList<Block>();
		String dir = dir1 + "_" + dir2;
		Block feet = diagFacing(dir1, dir2);
		Block head = feet.getFace(BlockFace.UP);
		Block aboveHead = head.getFace(BlockFace.UP);
		Block belowFeet = feet.getFace(BlockFace.DOWN);
		scanCone.add(feet);
		scanCone.add(head);
		scanCone.add(aboveHead);
		scanCone.add(belowFeet);

		// next row
		Block feetNext = feet.getFace(BlockFace.valueOf(dir));
		Block headNext = head.getFace(BlockFace.valueOf(dir));
		Block aboveNext = aboveHead.getFace(BlockFace.valueOf(dir));
		Block belowNext = belowFeet.getFace(BlockFace.valueOf(dir));
		scanCone.add(feetNext);
		scanCone.add(headNext);
		scanCone.add(aboveNext);
		scanCone.add(belowNext);
		
		// expand out
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir1)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir2)));
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir1)));
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir2)));
		scanCone.add(aboveNext.getFace(BlockFace.valueOf(dir1)));
		scanCone.add(aboveNext.getFace(BlockFace.valueOf(dir2)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir1)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir2)));
		
		// last one
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir)));
		
		return scanCone;		
	}
	
	public ArrayList<Block> northSouth(String dir) {
		ArrayList<Block> scanCone = new ArrayList<Block>();
		Block feet = blockFacing(dir);
		Block head = feet.getFace(BlockFace.UP);
		Block aboveHead = head.getFace(BlockFace.UP);
		Block belowFeet = feet.getFace(BlockFace.DOWN);
		scanCone.add(feet);
		scanCone.add(head);
		scanCone.add(aboveHead);
		scanCone.add(belowFeet);
		
		// next row
		Block feetNext = feet.getFace(BlockFace.valueOf(dir));
		Block headNext = head.getFace(BlockFace.valueOf(dir));
		Block aboveNext = aboveHead.getFace(BlockFace.valueOf(dir));
		Block belowNext = belowFeet.getFace(BlockFace.valueOf(dir));
		scanCone.add(feetNext);
		scanCone.add(headNext);
		scanCone.add(aboveNext);
		scanCone.add(belowNext);
		
		// expand out
		scanCone.add(feetNext.getFace(BlockFace.EAST));
		scanCone.add(feetNext.getFace(BlockFace.WEST));
		scanCone.add(headNext.getFace(BlockFace.EAST));
		scanCone.add(headNext.getFace(BlockFace.WEST));
		scanCone.add(aboveNext.getFace(BlockFace.EAST));
		scanCone.add(aboveNext.getFace(BlockFace.WEST));
		scanCone.add(belowNext.getFace(BlockFace.EAST));
		scanCone.add(belowNext.getFace(BlockFace.WEST));
		
		// last row
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir)));
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir + "_WEST")));
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir + "_EAST")));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir + "_WEST")));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir + "_EAST")));
		
		return scanCone;
	}
	
	public ArrayList<Block> eastWest(String dir) {
		ArrayList<Block> scanCone = new ArrayList<Block>();
		Block feet = blockFacing(dir);
		Block head = feet.getFace(BlockFace.UP);
		Block aboveHead = head.getFace(BlockFace.UP);
		Block belowFeet = feet.getFace(BlockFace.DOWN);
		scanCone.add(feet);
		scanCone.add(head);
		scanCone.add(aboveHead);
		scanCone.add(belowFeet);
		
		// next row
		Block feetNext = feet.getFace(BlockFace.valueOf(dir));
		Block headNext = head.getFace(BlockFace.valueOf(dir));
		Block aboveNext = aboveHead.getFace(BlockFace.valueOf(dir));
		Block belowNext = belowFeet.getFace(BlockFace.valueOf(dir));
		scanCone.add(feetNext);
		scanCone.add(headNext);
		scanCone.add(aboveNext);
		scanCone.add(belowNext);
		
		// expand out
		scanCone.add(feetNext.getFace(BlockFace.NORTH));
		scanCone.add(feetNext.getFace(BlockFace.SOUTH));
		scanCone.add(headNext.getFace(BlockFace.NORTH));
		scanCone.add(headNext.getFace(BlockFace.SOUTH));
		scanCone.add(aboveNext.getFace(BlockFace.NORTH));
		scanCone.add(aboveNext.getFace(BlockFace.SOUTH));
		scanCone.add(feetNext.getFace(BlockFace.NORTH));
		scanCone.add(feetNext.getFace(BlockFace.SOUTH));
		
		// last row
		scanCone.add(headNext.getFace(BlockFace.valueOf(dir)));
		scanCone.add(headNext.getFace(BlockFace.valueOf("NORTH_" +dir)));
		scanCone.add(headNext.getFace(BlockFace.valueOf("SOUTH_" + dir)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf(dir)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf("NORTH_" +dir)));
		scanCone.add(feetNext.getFace(BlockFace.valueOf("SOUTH_" + dir)));
		
		return scanCone;
	}
	
	public void printCone(ArrayList<Block> cone) {
		StringBuilder string = new StringBuilder();
		for(Block i: cone) {
			string.append(", " + i.getType());
		}
		player.sendMessage(string.toString());
	}
	
	public void isMineral(ArrayList<Block> cone) {
		for(Block i: cone) {
			if (plugin.getBlocks().contains(i.getTypeId())) {
				player.sendMessage("�6BEEP");
				return;
			}
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public MineralScanner getPlugin() {
		return plugin;
	}
}

