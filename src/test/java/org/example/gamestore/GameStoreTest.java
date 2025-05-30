package org.example.gamestore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStoreTest {
    private GameStore gameStore;

    @BeforeEach
    void setUp() {
        gameStore = new GameStore();
    }

    // helper method to find a game in the store inventory
    private Game findGameInStore(String gameTitle) {
        for (Game game : gameStore.getGames()) {
            if (game.getName().equalsIgnoreCase(gameTitle)) {
                return game;
            }
        }
        return null;
    }

    // --- addToCart ---

    @Test
    @DisplayName("Should add a game to the cart and reduce its quantity.")
    void testAddToCartSuccess() {
        String gameTitle = "Minecraft";
        Game minecraftGame = findGameInStore(gameTitle);
        assertNotNull(minecraftGame); // ensure the game exists before testing

        int initialQuantity = minecraftGame.getQuantity();

        boolean added = gameStore.addToCart(gameTitle);

        assertTrue(added);
        assertEquals(1, gameStore.getCart().size());
        assertEquals(gameTitle, gameStore.getCart().getFirst().getName());
        assertEquals(initialQuantity - 1, minecraftGame.getQuantity());
    }

    @Test
    @DisplayName("Should fail due to a game not existing in the list.")
    void testAddToCartNonExistent() {
        String nonExistentGame = "NonExistent Game";

        boolean added = gameStore.addToCart(nonExistentGame);

        assertFalse(added);
        assertTrue(gameStore.getCart().isEmpty());
    }

    @Test
    @DisplayName("Should fail due to the game being out of stock.")
    void testAddToCartOutOfStock() {
        String gameTitle = "Minecraft";
        Game minecraftGame = findGameInStore(gameTitle);
        assertNotNull(minecraftGame);

        int initialStoreQuantity = minecraftGame.getQuantity();

        // deplete stock for example case
        for (int i = 0; i < initialStoreQuantity; i++) {
            gameStore.addToCart(gameTitle);
        }

        // verify that the stock is 0 and the cart is full
        assertEquals(0, minecraftGame.getQuantity());
        assertEquals(initialStoreQuantity, gameStore.getCart().size()); // the cart size should match initial stock for Minecraft

        boolean addedWhenOutOfStock = gameStore.addToCart(gameTitle);

        assertFalse(addedWhenOutOfStock);
        assertEquals(initialStoreQuantity, gameStore.getCart().size());
        assertEquals(0, minecraftGame.getQuantity());
    }

    // --- removeFromCart ---

    @Test
    @DisplayName("Should remove a game from the cart and increase its quantity.")
    void testRemoveFromCartSuccess() {
        String gameTitle = "Minecraft";
        Game minecraftGame = findGameInStore(gameTitle);
        assertNotNull(minecraftGame);

        int initialStoreQuantity = minecraftGame.getQuantity();

        gameStore.addToCart(gameTitle);
        assertEquals(1, gameStore.getCart().size());
        assertEquals(initialStoreQuantity - 1, minecraftGame.getQuantity());

        boolean removed = gameStore.removeFromCart(gameTitle);

        assertTrue(removed);
        assertTrue(gameStore.getCart().isEmpty());
        assertEquals(initialStoreQuantity, minecraftGame.getQuantity());
    }

    @Test
    @DisplayName("Should fail due to a game not being in the cart.")
    void testRemoveFromCartNonExistentInCart() {
        String gameTitle = "Minecraft";
        String nonExistentInCart = "Fantasy Life i: The Girl Who Steals Time";

        Game minecraftGame = findGameInStore(gameTitle);
        assertNotNull(minecraftGame);
        int initialMinecraftQuantity = minecraftGame.getQuantity();

        gameStore.addToCart(gameTitle);
        assertEquals(1, gameStore.getCart().size());
        assertEquals(initialMinecraftQuantity - 1, minecraftGame.getQuantity());

        boolean removed = gameStore.removeFromCart(nonExistentInCart);

        assertFalse(removed);
        assertEquals(1, gameStore.getCart().size()); // cart should still contain minecraft
        assertEquals(initialMinecraftQuantity - 1, minecraftGame.getQuantity());
    }

    @Test
    @DisplayName("Should fail due to the cart being empty.")
    void testRemoveFromCartEmptyCart() {
        String gameTitle = "Minecraft";

        assertTrue(gameStore.getCart().isEmpty());

        boolean removed = gameStore.removeFromCart(gameTitle);

        assertFalse(removed);
        assertTrue(gameStore.getCart().isEmpty()); // cart should remain empty
    }

    // --- calculateCartTotal ---

    @Test
    @DisplayName("Should pass after confirming the prices of multiple items were added correctly")
    void testCalculateCartTotalMultipleItems() {
        gameStore.addToCart("Minecraft");
        gameStore.addToCart("Call of Duty: Black Ops 6");

        double expectedTotal = 49.99 + 89.99;
        double actualTotal = gameStore.calculateCartTotal();

        assertEquals(expectedTotal, actualTotal);
        assertEquals(2, gameStore.getCart().size());
    }

    @Test
    @DisplayName("Should pass after confirming the price of one item carries over to cart total correctly.")
    void testCalculateCartTotalSingleItem() {
        gameStore.addToCart("Fantasy Life i: The Girl Who Steals Time");

        double expectedTotal = 79.99;
        double actualTotal = gameStore.calculateCartTotal();

        assertEquals(expectedTotal, actualTotal);
        assertEquals(1, gameStore.getCart().size());
    }

    @Test
    @DisplayName("Should pass after confirming the cart total shows as 0 for an empty cart.")
    void testCalculateCartTotalEmptyCart() {
        // cart is empty by default

        double expectedTotal = 0.0;
        double actualTotal = gameStore.calculateCartTotal();

        assertEquals(expectedTotal, actualTotal);
        assertTrue(gameStore.getCart().isEmpty());
    }

    // --- purchaseGames ---

    @Test
    @DisplayName("Should successfully purchase items and clear the cart.")
    void testPurchaseGamesSuccess() {
        gameStore.addToCart("Minecraft");
        gameStore.addToCart("Call of Duty: Black Ops 6");
        assertEquals(2, gameStore.getCart().size());

        boolean purchased = gameStore.purchaseGames();

        assertTrue(purchased);
        assertTrue(gameStore.getCart().isEmpty());
    }

    @Test
    @DisplayName("Should fail to purchase when the cart is empty.")
    void testPurchaseGamesEmptyCart() {
        // cart is empty by default
        assertTrue(gameStore.getCart().isEmpty());

        boolean purchased = gameStore.purchaseGames();

        assertFalse(purchased);
        assertTrue(gameStore.getCart().isEmpty()); // cart should remain empty
    }

    @Test
    @DisplayName("Should correctly clear cart after purchasing multiple identical items.")
    void testPurchaseGamesMultipleSameItem() {
        gameStore.addToCart("Minecraft");
        gameStore.addToCart("Minecraft");
        assertEquals(2, gameStore.getCart().size());

        boolean purchased = gameStore.purchaseGames();

        assertTrue(purchased);
        assertTrue(gameStore.getCart().isEmpty());
    }
}
