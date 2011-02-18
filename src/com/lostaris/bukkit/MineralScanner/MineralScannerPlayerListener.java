package com.lostaris.bukkit.MineralScanner;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author Lostaris
 */
public class MineralScannerPlayerListener extends PlayerListener {
	private final MineralScanner plugin;
	private Player player;

	public MineralScannerPlayerListener(MineralScanner instance) {
		plugin = instance;
	}

	// triggered when the item in a players hand changes
	public void onItemHeldChange (PlayerItemHeldEvent event) {

	}

	// triggered when a player moves
	public void onPlayerMove (PlayerMoveEvent event) {
		player = event.getPlayer();
		//player.sendMessage("standing on " + blockUnderPlayer().getType());
		player.sendMessage("facing " + blockFacing().getType());
	}

	public Block blockUnderPlayer() {
		Location loc = player.getLocation();
		Block blockUnder = player.getWorld().getBlockAt((int)Math.floor(loc.getX()),
				(int)Math.floor(loc.getY() - 0.5D), (int)Math.floor(loc.getZ()));		
		return blockUnder;		
	}

	public Block blockFacing() {
		Location loc = player.getLocation();
		Block facing = player.getWorld().getBlockAt((int)Math.floor(loc.getX()),
				(int)Math.floor(loc.getY()), (int)Math.floor(loc.getZ())).getFace(BlockFace.valueOf(direction()));
		return facing;		
	}
	
	public Block diagFacing(String dir1, String dir2) {
		Location loc = player.getLocation();
		Block facing = player.getWorld().getBlockAt((int)Math.floor(loc.getX()),
				(int)Math.floor(loc.getY()), (int)Math.floor(loc.getZ())).getFace(BlockFace.valueOf(dir1 + "_" + dir2));
		return facing;
	}

	public ArrayList<Block> scanArea() {
		ArrayList<Block> scanCone = new ArrayList<Block>();
		Block feet = blockFacing();
		Block head = blockFacing().getFace(BlockFace.UP);
		Block aboveHead = head.getFace(BlockFace.UP);
		scanCone.add(feet);
		scanCone.add(head);
		scanCone.add(aboveHead);

		// next row
		scanCone.add(feet.getFace(BlockFace.valueOf(direction())));
		scanCone.add(head.getFace(BlockFace.valueOf(direction())));
		scanCone.add(aboveHead.getFace(BlockFace.valueOf(direction())));

		return scanCone;		
	}

	public void printScanArea(ArrayList<Block> blocks) {
		player.sendMessage("In the direction you are facing there is:");
		player.sendMessage("At foot height "+ blocks.get(0).getType() +" at head height "
				+ blocks.get(1).getType() + " and above your head " + blocks.get(2).getType());
		player.sendMessage("Next row: At foot height "+ blocks.get(3).getType() +" at head height "
				+ blocks.get(4).getType() + " and above your head " + blocks.get(5).getType());
	}

	public String direction() {
		int r = (int)Math.abs((player.getLocation().getYaw() - 90.0F) % 360.0F);
		String dir;
		if (r < 23) {
			dir = "NORTH"; 
		} else {
			if (r < 68) {
				dir = "NORTH_EAST";
			} else {
				if (r < 113) {
					dir = "EAST";
				} else {
					if (r < 158) { 
						dir = "SOUTH_EAST"; 
					} else {
						if (r < 203) {
							dir = "SOUTH";
						} else {
							if (r < 248) {
								dir = "SOUTH_WEST";
							} else {
								if (r < 293) {
									dir = "WEST";
								}
								else {
									if (r < 338) {
										dir = "NORTH_WEST"; 
									} else {
										dir = "NORTH"; 
									}
								}
							}
						}
					}
				}
			}
		}
		return dir;
	}
	
	public ArrayList<Block> diagonal(String dir1, String dir2) {
		ArrayList<Block> scanCone = new ArrayList<Block>();
		String dir = dir1 + "_" + dir2;
		Block feet = diagFacing(dir1, dir2);
		Block head = feet.getFace(BlockFace.UP);
		Block aboveHead = head.getFace(BlockFace.UP);
		scanCone.add(feet);
		scanCone.add(head);
		scanCone.add(aboveHead);

		// next row
		scanCone.add(feet.getFace(BlockFace.valueOf(dir)));
		scanCone.add(head.getFace(BlockFace.valueOf(dir)));
		scanCone.add(aboveHead.getFace(BlockFace.valueOf(dir)));
		return null;		
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public MineralScanner getPlugin() {
		return plugin;
	}
}

