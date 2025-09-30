import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import java.util.*;

@SpringBootApplication
public class SpringBootJpaHibernateDemo {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaHibernateDemo.class, args);
    }
}


@Entity
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    public Product() {}
    public Product(String name, double price) {this.name=name; this.price=price;}
    public Long getId(){return id;} public String getName(){return name;} public double getPrice(){return price;}
    public void setName(String name){this.name=name;} public void setPrice(double price){this.price=price;}
}


interface ProductRepository extends JpaRepository<Product, Long> {}

@RestController
@RequestMapping("/api/products")
class ProductController {
    @Autowired private ProductRepository repo;

    // CREATE
    @PostMapping
    public Product addProduct(@RequestBody Product p){ return repo.save(p); }

    // READ all
    @GetMapping
    public List<Product> getAll(){ return repo.findAll(); }

    // READ by ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){ return repo.findById(id).orElse(null); }

    // UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,@RequestBody Product newP){
        return repo.findById(id).map(p->{
            p.setName(newP.getName()); p.setPrice(newP.getPrice());
            return repo.save(p);
        }).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){ repo.deleteById(id); return "Deleted product id " + id; }
}
