package com.lostaris.bukkit.MineralDetector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Lostaris
 */
public class MineralDetectorPlayerListener extends PlayerListener {
    private final MineralDetector plugin;
    private Player player;

    public MineralDetectorPlayerListener(MineralDetector instance) {
        plugin = instance;
    }
    
    public void onItemHeldChange (PlayerItemHeldEvent event) {
    	
    }

	public MineralDetector getPlugin() {
		return plugin;
	}

    //Insert Player related code here
	public String direction() {
		int r = (int)Math.abs((player.getLocation().getYaw() - 90.0F) % 360.0F);
	    String dir;
	    if (r < 23) { dir = "N";
	    }
	    else
	    {
	      if (r < 68) { dir = "NE";
	      }
	      else
	      {
	        if (r < 113) { dir = "E";
	        }
	        else
	        {
	          if (r < 158) { dir = "SE";
	          }
	          else
	          {
	            if (r < 203) { dir = "S";
	            }
	            else
	            {
	              if (r < 248) { dir = "SW";
	              }
	              else
	              {
	                if (r < 293) { dir = "W";
	                }
	                else
	                {
	                  if (r < 338) dir = "NW"; else
	                    dir = "N"; 
	                }
	              }
	            }
	          }
	        }
	      }
	    }
	    return dir;
	}
}

