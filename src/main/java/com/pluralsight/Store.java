package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    private static final String FILE_NAME = "products.csv";
    private static final ArrayList<Product> inventory = new ArrayList<Product>();
    private static final ArrayList<Product> cart = new ArrayList<Product>();
    private static final Scanner myScanner = new Scanner(System.in);

    private static boolean running = true;

    public static void main(String[] args) {

        double totalAmount = 0.0;
        loadInventory(FILE_NAME, inventory);


        System.out.println("Please enter your user name.");
        String userName = myScanner.nextLine();
        while (running) {
            System.out.println("Hello " + userName + " !");
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            String response = myScanner.nextLine();

            // Call the appropriate method based on user choice
            switch (response) {
                case "1":
                    displayProducts(inventory, cart, myScanner);
                    break;
                case "2":
                    displayCart(cart, myScanner, totalAmount, userName);
                    break;
                case "3":
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        myScanner.close();
    }

    public static void loadInventory(String FILE_NAME, ArrayList<Product> inventory) {
        try {
            FileReader reader = new FileReader(FILE_NAME);
            BufferedReader buff = new BufferedReader(reader);
            String line;
            while ((line = buff.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2]);
                    Product product = new Product(id, name, price);
                    inventory.add(product);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found, please enter file name again!" + e.getMessage());


        } catch (IOException e) {
            System.out.println("Error occurred when reading from the file." + e.getMessage());
        }
    }

    // This method should read a CSV file with product information and
    // populate the inventory ArrayList with Product objects. Each line
    // of the CSV file contains product information in the following format:
    // id,name,price
    // where id is a unique string identifier, name is the product name,
    // price is a double value representing the price of the product

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner myScanner) {
        for (Product product : inventory) {
            System.out.println("ID: " + product.getId() + " Product Name: " + product.getName() + " Price: " + product.getPrice());

        }
        System.out.println("Enter the ID of the product you would like to add to the cart.");
        String id = myScanner.nextLine().trim();
        System.out.println("Enter the name of the product you would like to add to the cart.");
        String name = myScanner.nextLine().trim();
        Product productSelected = findProductById(id);
        if (productSelected != null) {

            cart.add(productSelected);
            System.out.println("\nProduct added to cart: " + productSelected.getName() + "\n");

        } else {

            System.out.println("\nProduct not found in inventory.\n");

        }


        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
    }

    public static double calculatePrice(ArrayList<Product> cart) {
        double totalAmount = 0;
        for (Product product : cart) {
            double productPrice = product.getPrice();
            totalAmount += productPrice;
        }
        return totalAmount;
    }


    public static void displayCart(ArrayList<Product> cart, Scanner myScanner, double totalAmount, String userName) {

        System.out.println(userName + "'s cart: ");
        for (Product product : cart) {
            System.out.println(product.getName());
        }
        totalAmount = calculatePrice(cart);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Options: ");
        System.out.println("1. Check Out");
        System.out.println("2. Back to Home");
        int choice = myScanner.nextInt();
        myScanner.nextLine();
        switch (choice) {
            case 1:
                checkOut(cart, totalAmount);
                break;
            case 2:
                System.out.println("Enter the ID of the product you want to remove from your cart (or type 'Back' to go back):");
                String input = myScanner.nextLine();
                if (!input.equalsIgnoreCase("Back")) {
                    Product product = findProductById(input);
                    if (product != null) {
                        cart.remove(product);
                        System.out.println(product.getName() + " has been removed from your cart.");
                    } else {
                        System.out.println("Product not found in your cart!");
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    //System.out.println(userName);
    // This method should display the items in the cart ArrayList, along
    // with the total cost of all items in the cart. The method should
    // prompt the user to remove items from their cart by entering the ID
    // of the product they want to remove. The method should update the cart ArrayList and totalAmount
    // variable accordingly.


    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Cart summary: " + totalAmount);
        for (Product product : cart) {
            System.out.println(product.getName() + ": $" + product.getPrice());
        }
        System.out.println("Total amount: " + totalAmount);

        System.out.println("Confirm purchase? (yes/no): ");
        String response = myScanner.nextLine();
        if (response.equals("yes")) {
            System.out.println("Purchase confirmed! Total amount of $" + totalAmount);
        } else {
            System.out.println("Purchase cancelled!");
        }

        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
    }

    public static Product findProductById(String id) {
        // This method should search the inventory ArrayList for a product with
// the specified ID, and return the corresponding Product object. If
// no product with the specified ID is found, the method should return null.
        for (Product product : inventory) { //this searches entire array list
            if (id.equalsIgnoreCase(product.getId())) {
                return product;
            }
        }
        return null;
    }
}