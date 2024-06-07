package main.java.com.monstermayhem;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.json.JSONObject;

public class JoinGameHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(requestBody);
            String gameId = json.getString("game_id");
            String playerId = json.getString("player_id");

            Game game = GameState.games.get(gameId);
            if (game != null) {
                game.players.putIfAbsent(playerId, new ArrayList<>());
                String response = "{\"game_id\": \"" + gameId + "\"}";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "{\"error\": \"Game not found\"}";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}