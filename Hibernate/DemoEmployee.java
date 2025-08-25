import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "salary")
    private double salary;

    public Employee() {}
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
    }
}

public class HibernateCRUDDemo {
    public static void main(String[] args) {
     
        Configuration cfg = new Configuration()
                .configure("hibernate.cfg.xml") 
                .addAnnotatedClass(Employee.class);

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        // CREATE (Insert)
        Transaction tx = session.beginTransaction();
        Employee e1 = new Employee("Govinda", 50000);
        Employee e2 = new Employee("Aniket", 60000);
        session.save(e1);
        session.save(e2);
        tx.commit();
        System.out.println("Employees Inserted!");

     
        Employee emp = session.get(Employee.class, 1); 
        System.out.println("Fetched Employee: " + emp);

   
        tx = session.beginTransaction();
        emp.setSalary(70000);
        session.update(emp);
        tx.commit();
        System.out.println("Updated Employee: " + emp);

        tx = session.beginTransaction();
        Employee empToDelete = session.get(Employee.class, 2);
        if(empToDelete != null) {
            session.delete(empToDelete);
            System.out.println("Deleted Employee: " + empToDelete);
        }
        tx.commit();

        session.close();
        factory.close();
    }
}
