package com.matthew.plugin.connectfour.game;

import com.matthew.plugin.connectfour.ConnectFourPlugin;
import com.matthew.plugin.connectfour.apis.Game;
import com.matthew.plugin.connectfour.game.mechanics.ConnectFourBoardMechanic;
import com.matthew.plugin.connectfour.modules.game.GameModule;
import com.matthew.plugin.connectfour.modules.manager.ServerModuleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConnectFourBoardGame implements Game {

    private final ConnectFourBoardMechanic mechanic;

    private final GameModule module;

    /**
     * Construct a new connect four game using two players. Set the turn to player1 > add both players to the games
     * players hashmap (player1 is key, player 2 is value) > add the new game to the ConnectFourManager classes arraylist >
     * call the private createBoard method.
     *
     * @param player1 - one of the players (the player who started the game & yellow blocks)
     * @param player2 - one of the players (red blocks)
     */
    public ConnectFourBoardGame(final Player player1, final Player player2) {
        final ServerModuleManager moduleManager = ServerModuleManager.getInstance();
        this.module = moduleManager.getRegisteredModule(GameModule.class);

        this.mechanic = new ConnectFourBoardMechanic(Arrays.asList(player1, player2));
    }

    @Override
    public List<Player> getPlayers() {
        return this.mechanic.getPlayers();
    }

    /**
     * Get the winner of the game
     *
     * @return the winner
     */
    @Override
    public Player getWinner() {
        return this.mechanic.getWinner();
    }

    /**
     * Get whose turn it is to place down a blocks
     *
     * @return whoever turn it is
     */
    @Override
    public Player getTurn() {
        return this.mechanic.getTurn();
    }

    /**
     * Send a message to the players in the same game
     *
     * @param message - message for the players which will display in chat
     */
    @Override
    public void sendMessage(String message) {
        for (Player player : getPlayers()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void startGame() {
        this.mechanic.setTurn(getPlayers().get(0)); //Player 1 starts
        this.mechanic.spawnBoard();
        this.module.addGame(this);
    }

    /**
     * End the game by removing the board after 2 seconds so the player who lost can see how they lost > remove the players
     * from the players hashmap > remove the game from the ConnectFourManager's boards list
     */
    @Override
    public void stopGame() {
        //Remove board
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block block : region.getBlocks()) {
                    block.setType(Material.AIR);
                }
                players.remove(player1);
                GameModule.getGames().remove(GameModule.getGame(player1));
                cancel();
            }

        }.runTaskTimer(ConnectFourPlugin.getInstance(), 40L, 0L);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConnectFourBoardGame check = (ConnectFourBoardGame) o;
        return Objects.equals(getPlayers().get(0), check.getPlayers().get(0))
                && Objects.equals(getPlayers().get(1), check.getPlayers().get(1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayers().get(0), getPlayers().get(1));
    }
}
