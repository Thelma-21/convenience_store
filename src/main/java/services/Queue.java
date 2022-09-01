package services;

import enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import models.Product;
import models.User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Queue {
    private LinkedList<User> queue = new LinkedList<>();


    public void addToQueue(User user){
        if(user.getRole().equals(Roles.CUSTOMER)) this.queue.add(user);
    }

    public LinkedList<User> processQueue(){
        for (int i = 0; i < queue.size(); i++){
            for (int j = i; j < queue.size(); j++){
                User user1 = queue.get(i);
                User user2 = queue.get(j);
                Product user1Product = new Product();
                Product user2Product = new Product();
                for (Map.Entry<Product,Integer> entry : user1.getCart().entrySet()){
                    user1Product = entry.getKey();
                }
                for (Map.Entry<Product,Integer> entry : user2.getCart().entrySet()){
                    user2Product = entry.getKey();
                }
                if (user1Product.equals(user2Product)){
                    if (user1.getCart().get(user1Product) < user2.getCart().get(user2Product)){
                        queue.remove(j);
                        queue.remove(i);
                        queue.add(i,user2);
                        queue.add(j,user1);
                        i = -1;
                        break;
                    }
                }
            }
        }
        return queue;
    }
}
