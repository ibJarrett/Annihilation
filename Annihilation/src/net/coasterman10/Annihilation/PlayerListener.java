package net.coasterman10.Annihilation;

import net.coasterman10.Annihilation.maps.MapManager;
import net.coasterman10.Annihilation.teams.Team;
import net.coasterman10.Annihilation.teams.TeamManager;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
    private final Annihilation plugin;
    private final MapManager mapManager;
    private final TeamManager teamManager;

    public PlayerListener(Annihilation plugin) {
	plugin.getServer().getPluginManager().registerEvents(this, plugin);
	this.plugin = plugin;
	mapManager = plugin.getMapManager();
	teamManager = plugin.getTeamManager();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
	Player player = e.getPlayer();
	Team team = teamManager.getTeamWithPlayer(player.getName());
	if (team.getName().equalsIgnoreCase("lobby") || !team.isAlive()
		|| plugin.getPhase() == 0) {
	    e.setRespawnLocation(mapManager.getLobbySpawnPoint());
	} else {
	    e.setRespawnLocation(mapManager.getSpawnPoint(team.getName()));
	}
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
	Player player = e.getPlayer();
	Team team = teamManager.getTeamWithPlayer(player.getName());
	if (team.getName().equalsIgnoreCase("lobby") || !team.isAlive()
		|| plugin.getPhase() == 0) {
	    player.teleport(mapManager.getLobbySpawnPoint());
	} else {
	    player.teleport(mapManager.getSpawnPoint(team.getName()));
	}
    }
}