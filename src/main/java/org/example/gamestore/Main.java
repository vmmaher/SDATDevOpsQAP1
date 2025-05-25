package org.example.gamestore;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameStore store = new GameStore();
        Scanner scanner = new Scanner(System.in);
        boolean programActive = true;

        System.out.println("Welcome to the Game Store CLI");

        while (programActive) {
            System.out.println("\n Select an option:");
            System.out.println("1. View Games");
            System.out.println("2. Add Game to Cart");
            System.out.println("3. Remove Game from Cart");
            System.out.println("4. View Cart & Total ");
            System.out.println("5. Purchase Cart");
            System.out.println("6. Exit ");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    store.displayGames();
                    break;
                case 2:
                    System.out.println("Enter the title of the game to add to your cart: ");
                    String gameAddingToCart = scanner.nextLine();
                    store.addToCart(gameAddingToCart);
                    break;
                case 3:
                    System.out.println("Enter the title of the game to remove from your cart:");
                    String gameRemovingFromCart = scanner.nextLine();
                    store.removeFromCart(gameRemovingFromCart);
                    break;
                case 4:
                    if (store.getCart().isEmpty()) {
                        System.out.println("Your cart is empty right now.");
                    } else {
                        System.out.println("\n--- Your Cart ---");
                        for (Game game : store.getCart()) {
                            System.out.printf("%s - $%.2f%n", game.getName(), game.getPrice());
                        }
                        System.out.printf("------------------%n");
                        System.out.printf("Total: $%.2f%n", store.calculateCartTotal());
                    }
                    break;
                case 5:
                    store.purchaseGames();
                    break;
                case 6:
                    programActive = false;
                    System.out.println("Thank you for your patronage!");
                    break;
                default:
                    System.out.println("Invalid choice selected - please try again");
            }
        }
        scanner.close();
    }
}