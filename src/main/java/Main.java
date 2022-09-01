import enums.Roles;
import models.Store;
import models.User;
import services.Queue;
import services.UserService;
import java.util.LinkedList;



public class Main {
    public static void main(String[] args) {
        User manager = new User("kay", Roles.MANAGER);
        User applicant = new User("Ben", Roles.APPLICANT, 60);
        Store store = new Store();
        UserService userService = new UserService(store);
        User cashier = userService.hireStaff(manager, applicant);
        //System.out.println(cashier);

        User customer1 = new User("customer1", Roles.CUSTOMER, 6000.0);
        User customer2 = new User("customer2", Roles.CUSTOMER, 4000.0);
        User customer3 = new User("customer3", Roles.CUSTOMER, 7000.0);
        User customer4 = new User("customer4", Roles.CUSTOMER, 4500.0);
        User customer5 = new User("customer5", Roles.CUSTOMER, 5500.0);
        User customer6 = new User("customer6", Roles.CUSTOMER, 8000.0);
        User customer7 = new User("customer7", Roles.CUSTOMER, 2000.0);

         userService.addToProductList();
        System.out.println(store.getProductList());


        userService.addToCart(customer1,"Bran",40);
        userService.addToCart(customer2,"Arrowroot",30);
        userService.addToCart(customer3,"Chocolate Chip",10);
        userService.addToCart(customer4,"Potato Chips",20);
        userService.addToCart(customer5,"Arrowroot",50);
        userService.addToCart(customer6,"Bran",70);
        userService.addToCart(customer7,"Potato Chips",30);

        Queue queue = new Queue();
        queue.addToQueue(customer1);
        queue.addToQueue(customer2);
        queue.addToQueue(customer3);
        queue.addToQueue(customer4);
        queue.addToQueue(customer5);
        queue.addToQueue(customer6);
        queue.addToQueue(customer7);

        LinkedList<User> line = queue.processQueue();
        userService.sellProduct(line, cashier);




    }
}