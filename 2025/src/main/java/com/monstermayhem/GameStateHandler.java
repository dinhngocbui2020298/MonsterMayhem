package main.java.com.monstermayhem;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class GameStateHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("=");
            String gameId = params[1];

            Game game = GameState.games.get(gameId);
            if (game != null) {
                JSONObject responseJson = new JSONObject();
                responseJson.put("grid", game.grid);
                String response = responseJson.toString();
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
