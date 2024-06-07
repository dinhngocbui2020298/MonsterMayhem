package main.java.com.monstermayhem;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class NewGameHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String gameId = UUID.randomUUID().toString();
            Game game = new Game(gameId);
            GameState.games.put(gameId, game);

            String response = "{\"game_id\": \"" + gameId + "\"}";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}