package models;

import enums.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
@NoArgsConstructor
@Getter
@Setter
public class User{
    private String name;
    private Roles role;
    private Double wallet;
    private int score;
    private HashMap<Product, Integer> cart = new HashMap<>();

    public User(String name, Roles role) {
        this.name = name;
        this.role = role;
    }

    public User(String name, Roles role, Double wallet) {
        this.name = name;
        this.role = role;
        this.wallet = wallet;
    }

    public User(String name, Roles role, int score) {
        this.name = name;
        this.role = role;
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", wallet=" + wallet +
                ", score=" + score +
                '}';
    }

}
