package com.lostaris.bukkit.MineralScanner;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * MineralDetector block listener
 * @author Lostaris
 */
public class MineralScannerBlockListener extends BlockListener {
    private final MineralScanner plugin;

    public MineralScannerBlockListener(final MineralScanner plugin) {
        this.plugin = plugin;
    }

	public MineralScanner getPlugin() {
		return plugin;
	}

    //put all Block related code here
}
