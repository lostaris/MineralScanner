package com.lostaris.bukkit.MineralDetector;

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
public class MineralDetectorBlockListener extends BlockListener {
    private final MineralDetector plugin;

    public MineralDetectorBlockListener(final MineralDetector plugin) {
        this.plugin = plugin;
    }

	public MineralDetector getPlugin() {
		return plugin;
	}

    //put all Block related code here
}
