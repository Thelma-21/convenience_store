package services;

import enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Product;
import models.Store;
import models.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserService {
    private Store store;

    public User hireStaff(User manager, User user){
        if(manager.getRole().equals(Roles.MANAGER) && user.getRole().equals(Roles.APPLICANT)){
            if(user.getScore() > 50){
                return new User(user.getName(), Roles.CASHIER);
            }else {
                System.out.println("Sorry applicant not qualified");
            }
        }else {
            System.out.println("User not authorized");
        }
        return user;
    }


    public void addToCart(User user, String productName, int amount){
        if(user.getRole().equals(Roles.CUSTOMER)){
            for(Product product : store.getProductList()){
//                System.out.println("-----------------"+ store.getProductList() + "============================================");
                if(productName.equals(product.getName())){
                    if(removeFromProductList(product, amount)){
                        HashMap<Product, Integer> trolley = user.getCart();
                        trolley.put(product, amount);
                        user.setCart(trolley);

                    }
                }
            }
        }else {
            System.out.println("User not a Customer");
        }
    }



    public long checkAvailability(Store store, Product product){
       for (Product product1 : store.getProductList()){
           if(product1.equals(product)){
               return product1.getQuantity();
           }
       }
        System.out.println("Product out of stock");
       return 0;
    }

    public boolean removeFromProductList( Product product, long quantity){
        long availableQuantity = checkAvailability(store, product);
        if(availableQuantity >= quantity){
            product.setQuantity(availableQuantity - quantity);
            return true;
        }else {
            System.out.println("Sorry, we only have " + availableQuantity + " " + product.getName() + " left");
            return false;
        }
    }


    public void addToProductList(){

        String input;
        BufferedReader br;
        {
            try {
                br = new BufferedReader(new FileReader("/Users/dec/IdeaProjects/Week3Task/ProductList.csv"));
                ArrayList<Product> listOfProducts = store.getProductList();
                while ((input = br.readLine()) != null){
                    boolean added = false;
                    String [] values = input.split(",");
                    if(listOfProducts.size() == 0){
                        Product newProduct = new Product(values[0], values[1], Long.parseLong(values[2]), Double.parseDouble(values[3]));
                        listOfProducts.add(newProduct);
                        added = true;
                    }
                    for (Product product : listOfProducts){
                        if(product.getName().equals(values[1]) && !added){
                            product.setQuantity(product.getQuantity() + Long.parseLong(values[2]));
                            added = true;
                        }

                    }
                    if(!added){
                        Product newProduct = new Product(values[0], values[1], Long.parseLong(values[2]), Double.parseDouble(values[3]));
                        listOfProducts.add(newProduct);
                    }
                }
                store.setProductList(listOfProducts);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void sellProduct(LinkedList<User> queue, User cashier){
        if(cashier.getRole().equals(Roles.CASHIER)){
            for(User user : queue){
                Product product = new Product();
                for (Map.Entry<Product, Integer> entry : user.getCart().entrySet()){
                    product = entry.getKey();
                }
                int quantity = user.getCart().get(product);
                double price = product.getPrice() * quantity;
                if(user.getWallet() >= price){
                    user.setWallet(user.getWallet() - price);
                    dispenseReceipt(user, quantity, product, price);
                }else{
                    System.out.println("Insufficient Fund");
                }
            }
        }else {
            System.out.println("User not authorized for this transaction");
        }

    }


    public void dispenseReceipt(User customer, Integer quantity, Product product, double price){
        System.out.println("\t \t Name: "+ customer.getName() + "\t \t \n");
        System.out.println("Product Name \t\t\t\t Quantity \t\t\t\t price\n");
        System.out.println(product.getName() +"\t\t\t\t\t\t " + quantity + "\t\t\t\t\t\t  " + product.getPrice());
        System.out.println("Total : " + price + "\n\n");
    }
}
