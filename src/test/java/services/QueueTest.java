package services;

import enums.Roles;
import models.Product;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Queue")
class QueueTest {

    private Queue queue;

    @BeforeEach
    void setUp() {
        queue = new Queue();
    }

    @Test
    @DisplayName("Should sort the queue by product and quantity")
    void processQueueShouldSortTheQueueByProductAndQuantity() {
        User user1 = new User("user1", Roles.CUSTOMER);
        User user2 = new User("user2", Roles.CUSTOMER);
        User user3 = new User("user3", Roles.CUSTOMER);
        Product product1 = new Product("category1", "product1", 10, 10.0);
        Product product2 = new Product("category2", "product2", 20, 20.0);
        Product product3 = new Product("category3", "product3", 30, 30.0);
        user1.getCart().put(product1, 1);
        user2.getCart().put(product1, 2);
        user3.getCart().put(product2, 1);
        queue.addToQueue(user1);
        queue.addToQueue(user2);
        queue.addToQueue(user3);

        LinkedList<User> result = queue.processQueue();

        assertEquals(user2, result.get(0));
        assertEquals(user1, result.get(1));
        assertEquals(user3, result.get(2));
    }

    @Test
    @DisplayName("Should add the user to the queue when the role is customer")
    void addToQueueWhenRoleIsCustomer() {
        User user = new User("John", Roles.CUSTOMER);
        queue.addToQueue(user);
        assertEquals(1, queue.getQueue().size());
    }

    @Test
    @DisplayName("Should not add the user to the queue when the role is not customer")
    void addToQueueWhenRoleIsNotCustomer() {
        User user = new User("John", Roles.MANAGER);
        queue.addToQueue(user);
        assertEquals(0, queue.getQueue().size());
    }
}