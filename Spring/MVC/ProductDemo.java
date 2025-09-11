
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
public class ShoppingMvcDemo {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingMvcDemo.class, args);
    }
}


class Product {
    private int id; private String name; private double price;
    public Product(int id,String name,double price){this.id=id;this.name=name;this.price=price;}
    public int getId(){return id;} public String getName(){return name;} public double getPrice(){return price;}
}

/
@RestController
@RequestMapping("/shop")
class ShoppingController {
    private List<Product> products = new ArrayList<>(
        Arrays.asList(new Product(1,"Laptop",70000), new Product(2,"Phone",30000), new Product(3,"Headphones",2000))
    );

 
    @GetMapping("/products")
    public List<Product> getProducts() { return products; }

  
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {
        return products.stream().filter(p -> p.getId()==id).findFirst().orElse(null);
    }


    @PostMapping("/products")
    public String addProduct(@RequestBody Product p) {
        products.add(p);
        return "Product added: " + p.getName();
    }
}
