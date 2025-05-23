package org.example.gamestore;

import java.util.ArrayList;
import java.util.List;

public class GameStore {
    private List<Game> games;

    public GameStore() {
        games = new ArrayList<>();
        games.add(new Game("Minecraft", 49.99, 11));
        games.add(new Game("Call of Duty: Black Ops 6", 89.99, 27));
        games.add(new Game("Fantasy Life i: The Girl Who Steals Time", 79.99, 15));
    }

    public List<Game> getGames() {
        return games;
    }

    public void displayGames() {
        System.out.println("Physical games currently in stock:");
        for (Game game : games) {
            System.out.printf("%s - $%.2f (%d in stock) %n",
                    game.getName(), game.getPrice(), game.getQuantity());
      ;  }
    }
}

