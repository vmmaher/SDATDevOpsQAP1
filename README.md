
# Game Store CLI Application - QAP 1
This project is a simple Java CLI for a game store. It allows users to browse the available games, add them to their cart, remove them, view the cart's contents and total, and purchase the games. It uses JUnit 5 for unit testing and GitHub Actions for automated testing.
## How it Works
The application has three classes:
1.  **`Game.java`**: This class represents a single game in the store. It holds the game's `name`, `price`, and `quantity` in stock.
2.  **`GameStore.java`**: This is the main logic for the game store. It maintains two `ArrayList` - `games` (the store's inventory) and `cart` (the customer's cart).
    * `displayGames()`: Prints the current inventory to the terminal.
    * `addToCart(String gameTitle)`: Searches for a game by title. If it is found and is in stock, it decreases the game's quantity and adds it to the `cart`.
    * `removeFromCart(String gameTitle)`: Searches for a game in the `cart`. If it is found, it removes it from the `cart` and increases its quanity.
    * `calculateCartTotal()`: Goes through the `cart` and sums up the prices of all games.
    * `purchaseGames()`: Clears the `cart` and shows a successful purchase and shows the final total.
3.  **`Main.java`**:  initializes the `GameStore` object and uses `Scanner` to handle user input before showing the menu with options to the user, then calls the correct methods based on the choice.
## Clean Code Practices
Clean code practices have been applied throughout the application to help with readability and maintainability. Here are some examples:
### 1. Meaningful Names
All the variables, methods, and classes are named with accurate descriptions which helps make their purpose clear.
**Example from `GameStore.java`:**
```java
public boolean addToCart(String gameTitle) {
    // using optional for easier search
    Optional<Game> foundGameOptional = games.stream()
            .filter(game -> game.getName().equalsIgnoreCase(gameTitle))
            .findFirst();
```
-   `addToCart`, `gameTitle`, and `foundGameOptional` are all clear names demonstrating the purpose
-
A screenshot of this code is included in the repository, per assignment instructions.

### 2. Single Responsibility Principle
Each class and method is designed to have a single responsibility.
**Example from `Game.java`:**
```java
public class Game {  
    private String name;  
    private double price;  
    private int quantity;  
  
    public Game(String name, double price, int quantity) {  
        this.name = name;  
        this.price = price;  
        this.quantity = quantity;  
    }  
  
    public String getName() { return name; }
    // remaining code
}
```
The `Game` class represents a single game and manages its properties. It doesn't handle any store logic, cart operations, or any user input. This separation makes the code easier to understand, test, and modify.

A screenshot of this code is included in the repository, per assignment instructions.

### 3. Concise Methods
Methods are kept short and they focus on one task, which avoids unnecessary complexity.
**Example from `GameStore.java`:**
```java
public double calculateCartTotal() {
    double total = 0.0;
    for (Game game : cart) {
        total += game.getPrice();
    }
    return total;
}
```
The `calculateCartTotal()` method is clear - it only calculates the sum of prices inside of the cart. It doesn't display any totals, nor does it handle purchases or modify any game quantities, which means it enforces a single focused responsibility.

A screenshot of this code is included in the repository, per assignment instructions.

## Unit Tests

The unit tests are written using JUnit 5. The `GameStoreTest.java` file contains all of the tests that cover various scenarios, including positive and negative results, edge cases, and error handling.
### Test Cases
Some of the test cases (not all for brevity):
-   **`testAddToCartSuccess()`**:
    -   Tests successfully adding an available game to the cart.
    -   Checks if the game was added to the cart, the cart size increased, and the game's quantity in the store decreased by one.
-   **`testAddToCartNonExistent()`**:
    -   Tests attempting to add a game that does not exist in the store's inventory.
    -   Verifies that the `addToCart` method returns `false` and the cart remains empty.
-   **`testAddToCartOutOfStock()`**:
    -   Tests attempting to add a game that is currently out of stock.
    -   Ensures that the `addToCart` method returns `false` when the game's quantity is zero, and the cart size doesn't change beyond the initial stock.

A screenshot of the tests passing is included in the repository, per assignment instructions, but it is also viewable on GitHub Actions.

## Dependencies
-   **JUnit**
    -   **Group ID**: `org.junit.jupiter`
    -   **Artifact ID**: `junit-jupiter-engine`
-   **JUnit Params**
    -   **Group ID**: `org.junit.jupiter`
    -   **Artifact ID**: `junit-jupiter-params`

These dependencies are automatically downloaded and managed by Maven when the project is built.

A screenshot of pom.xml is included in the repository, per assignment instructions.

## GitHub Actions

This project is configured with GitHub Actions to automate the build and test process. The workflow is defined in `.github/workflows/maven.yml`, and it ensures that each push and pull request triggers a build and runs all of the unit tests.

**Workflow Steps:**
1.  **Check Code**: Checks out the repository code.
2.  **Set up Java**: Configures the Java 23 environment.
3.  **Build and Test with Maven**: Executes the Maven `package` goal, which compiles the code and runs the unit tests.

A screenshot of the tests succeeding on GitHub is included in the repository, per assignment instructions.

## Problems Encountered

While working on this QAP, no significant problems were encountered, aside from some issues with configuring the file structure due to my lack of familiarity with IntelliJ.