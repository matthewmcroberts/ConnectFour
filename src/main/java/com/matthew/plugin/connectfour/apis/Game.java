package com.matthew.plugin.connectfour.apis;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public interface Game {

    /**
     * Start game and allocate necessary resources
     */
    void startGame();

    /**
     * End game and release any additionally allocated resources
     */
    void stopGame();

    /**
     * The logic that occurs after a player makes their move (right-clicks on the bottom row quartz block that they
     * wish to be their column for their block to be placed)
     *
     * @param player Bukkit Player who is currently in the game and is attempting to place a block
     * @param blockClicked Bottom row block that was clicked to designate which column the block is to attempt to be placed
     */
    void placeBlock(Player player, Block blockClicked);

    /**
     * Check if there is a winning sequence. A winning sequence is 4 blocks in a row horizontally, vertically, or diagonally
     *
     * @param player the player whose most recent play is being checked for a winning sequence
     * @return whether a winning sequence was found
     */
    boolean hasWinningSequence(Player player);

    /**
     * Send a message to the players in the same game
     *
     * @param message message for the players which will display in chat
     */
    void sendMessage(String message);

    /**
     * Get player 1
     *
     * @return the player who initiated the creation of the game
     */
    Player getPlayer1();

    /**
     * Get player 2
     *
     * @return the player who accepted the invite to play the game
     */
    Player getPlayer2();

    /**
     * Get all players currently playing the game. Assuming players is not null, there will always be 2
     *
     * @return list of players in the game
     */
    List<Player> getPlayers();

    /**
     * Get the winner of the game
     *
     * @return a Bukkit Player representing the winner of the game
     */
    Player getWinner();

    /**
     * Get whose turn it is to place down a block
     *
     * @return a Bukkit Player representing the player who is allowed to make their move
     */
    Player getTurn();
}
