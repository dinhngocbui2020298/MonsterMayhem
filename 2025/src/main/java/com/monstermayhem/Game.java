package main.java.com.monstermayhem;

import java.util.*;

public class Game {
    public String gameId;
    public Map<String, List<String>> players;
    public String[][] grid;

    public Game(String gameId) {
        this.gameId = gameId;
        this.players = new HashMap<>();
        this.grid = new String[10][10];
    }

    public void placeMonster(String playerId, String monster, int x, int y) {
        if (grid[x][y] == null) {
            grid[x][y] = playerId + ":" + monster;
            players.get(playerId).add(monster);
        }
    }

    public void moveMonster(String playerId, int fromX, int fromY, int toX, int toY) {
        if (grid[fromX][fromY] != null && grid[fromX][fromY].startsWith(playerId) && grid[toX][toY] == null) {
            grid[toX][toY] = grid[fromX][fromY];
            grid[fromX][fromY] = null;
        }
    }
}
