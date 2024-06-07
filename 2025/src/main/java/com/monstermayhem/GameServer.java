package main.java.com.monstermayhem;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class GameServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/new_game", new NewGameHandler());
        server.createContext("/join_game", new JoinGameHandler());
        server.createContext("/place_monster", new PlaceMonsterHandler());
        server.createContext("/move_monster", new MoveMonsterHandler());
        server.createContext("/game_state", new GameStateHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}