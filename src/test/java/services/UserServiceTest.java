package services;

import enums.Roles;
import models.Product;
import models.Store;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static Store store;
    static User manager;
    static User applicant;
    static UserService userService;

    @BeforeEach
    void setUp() {
        store = new Store();
        userService = new UserService(store);
        manager = new User("Kay", Roles.MANAGER);
        applicant = new User("Ada", Roles.APPLICANT, 60);


    }

        @Test
        @DisplayName("Should not dispense a receipt when the customer does not have enough money")
        void sellProductWhenCustomerDoesNotHaveEnoughMoneyThenDoNotDispenseReceipt() {
            User customer = new User("Ada", Roles.CUSTOMER, 100.0);
            Product product = new Product("Food", "Rice", 10, 100);
            HashMap<Product, Integer> cart = new HashMap<>();
            LinkedList<User> line = new LinkedList<>();
            User cashier = new User("Bill", Roles.CASHIER);
            cart.put(product, 1);
            customer.setCart(cart);
            userService.sellProduct(line, cashier);
            assertEquals(100.0, customer.getWallet());
        }

        @Test
        @DisplayName("Should dispense a receipt when the customer has enough money")
        void sellProductWhenCustomerHasEnoughMoneyThenDispenseReceipt() {
            User customer = new User("Ada", Roles.CUSTOMER, 100.0);
            Product product = new Product("Food", "Rice", 10, 10);
            HashMap<Product, Integer> cart = new HashMap<>();
            LinkedList<User> line = new LinkedList<>();
            User cashier = new User("Bill", Roles.CASHIER);
            cart.put(product, 2);
            customer.setCart(cart);
            userService.sellProduct(line, cashier);
        }

//        @Test
//        void addToCart() {
//            User customer = new User("Sam", Roles.CUSTOMER, 5000.0);
//            userService.addToCart(customer, "potato chips", 28);
//            Product product = new Product();
//            assertEquals(Roles.CUSTOMER, customer.getRole());
//
//
//
//        }

    @Test
    @DisplayName("Should not add the product to the cart when the user is not a customer")
    void addToCartWhenUserIsNotCustomer() {
        User user = new User("Ada", Roles.CASHIER);
        HashMap<Product, Integer> cart = new HashMap<>();
        user.setCart(cart);
        userService.addToCart(user, "Bread", 2);
        assertEquals(0, user.getCart().size());
    }

    @Test
    @DisplayName("Should add the product to the cart when the user is a customer")
    void addToCartWhenUserIsCustomer() {
        userService.addToProductList();
        User customer = new User("Ada", Roles.CUSTOMER);
        userService.addToCart(customer, "Carrot", 2);
        HashMap<Product, Integer> cart = customer.getCart();
        assertEquals(1, cart.size());
    }

    @Test
    void hireStaff() {
        User cashier = userService.hireStaff(manager, applicant);
        assertEquals(applicant.getName(), cashier.getName());
        assertNotEquals(applicant.getRole(), cashier.getRole());
    }

    @Test
    void checkAvailability() {
        Store store = new Store();
        Product product = new Product("bars", "carrot", 91, 1.77);
        long expectedValue = 0;
        for (Product product1 : store.getProductList()) {
            if (product1.equals(product)) {
                expectedValue = product1.getQuantity();
            }
            assertEquals(expectedValue, userService.checkAvailability(store, product));

        }

    }

    @Test
    void removedFromProductList(){


    }
}

