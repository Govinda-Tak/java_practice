import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "customers")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

  
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "customer_products",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public Customer() {}
    public Customer(String name) { this.name = name; }

    public void addProduct(Product p) {
        products.add(p);
        p.getCustomers().add(this);
    }

    public String toString() { return "Customer{id=" + id + ", name=" + name + "}"; }
}

@Entity
@Table(name = "products")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

    public Product() {}
    public Product(String name) { this.name = name; }

    public List<Customer> getCustomers() { return customers; }

    public String toString() { return "Product{id=" + id + ", name=" + name + "}"; }
}

public class ShoppingExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("shoppingPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();


        Customer c1 = new Customer("Govinda");
        Customer c2 = new Customer("Hansa");


        Product p1 = new Product("Laptop");
        Product p2 = new Product("Mobile");
        Product p3 = new Product("Shoes");

        c1.addProduct(p1);
        c1.addProduct(p3);
        c2.addProduct(p1);
        c2.addProduct(p2);


        em.persist(c1);
        em.persist(c2);

        em.getTransaction().commit();


        System.out.println("\nFetching Customers...");
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        for (Customer c : customers) {
            System.out.println(c);
            System.out.println("Products bought: " + c.products); 
        }

        em.close();
        emf.close();
    }
}
