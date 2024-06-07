package main.java.com.monstermayhem;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONObject;

public class PlaceMonsterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            JSONObject json = new JSONObject(requestBody);
            String gameId = json.getString("game_id");
            String playerId = json.getString("player_id");
            String monster = json.getString("monster");
            int x = json.getJSONArray("position").getInt(0);
            int y = json.getJSONArray("position").getInt(1);

            Game game = GameState.games.get(gameId);
            if (game != null && game.players.containsKey(playerId)) {
                game.placeMonster(playerId, monster, x, y);
                String response = "{\"status\": \"placed\"}";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "{\"error\": \"Invalid game or player\"}";
                exchange.sendResponseHeaders(400, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}