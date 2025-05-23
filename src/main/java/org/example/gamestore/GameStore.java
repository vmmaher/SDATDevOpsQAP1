package org.example.gamestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameStore {
    private final List<Game> games;
    private final List<Game> cart = new ArrayList<>();

    public GameStore() {
        games = new ArrayList<>();
        games.add(new Game("Minecraft", 49.99, 11));
        games.add(new Game("Call of Duty: Black Ops 6", 89.99, 27));
        games.add(new Game("Fantasy Life i: The Girl Who Steals Time", 79.99, 15));
    }

    public List<Game> getGames() {
        return games;
    }
    public List<Game> getCart() { return cart; }

    public void displayGames() {
        System.out.println("Physical games currently in stock:");
        for (Game game : games) {
            System.out.printf("%s - $%.2f (%d in stock) %n",
                    game.getName(), game.getPrice(), game.getQuantity());
        }
    }

    public boolean addToCart(String gameTitle) {
        // using optional for easier search
        Optional<Game> foundGameOptional = games.stream()
                .filter(game -> game.getName().equalsIgnoreCase(gameTitle))
                .findFirst();

        if (foundGameOptional.isPresent()) {
            Game game = foundGameOptional.get();

            if (game.getQuantity() <= 0) {
                System.out.println("This game is currently out of stock. Sorry!");
                return false;
            }

            game.setQuantity(game.getQuantity() - 1);
            cart.add(game);
            System.out.println(game.getName() + " added to cart.");
            return true;
        } else {
                System.out.println(gameTitle + " wasn't found!");
                return false;
        }
    }

    public boolean removeFromCart(String gameTitle) {
        // using optional for easier search
        Optional<Game> gameInCartOptional = cart.stream()
                .filter(game -> game.getName().equalsIgnoreCase(gameTitle))
                .findFirst();
        if (gameInCartOptional.isPresent()) {
            Game gameToRemove = gameInCartOptional.get();
            cart.remove(gameToRemove);

            Optional<Game> gameInStoreOptional = games.stream()
                    .filter(game -> game.getName().equalsIgnoreCase(gameTitle))
                    .findFirst();

            gameInStoreOptional.ifPresent(gameInStore -> gameInStore.setQuantity(gameInStore.getQuantity() + 1));
            System.out.println(gameToRemove.getName() + " removed from cart.");
            return true;
        } else {
            System.out.println(gameTitle + " wasn't found in your cart!");
            return false;
        }
    }

    public double calculateCartTotal() {
        double total = 0.0;
        for (Game game : cart) {
            total += game.getPrice();
        }
        return total;
    }

    public boolean purchaseGames() {
        // placeholder
        return false;
    }
}

