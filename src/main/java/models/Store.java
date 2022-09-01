package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Store {
    private ArrayList<Product> productList = new ArrayList<>();
}
