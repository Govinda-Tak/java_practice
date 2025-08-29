import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "employee")
class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    public Employee() {}
    public Employee(String name) {
        this.name = name;
    }

 
    public void setAddress(Address address) {
        this.address = address;
    }
    public void addProject(Project project) {
        projects.add(project);
        project.setEmployee(this);
    }
}

@Entity
@Table(name = "address")
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String city;
    private String state;

    public Address() {}
    public Address(String city, String state) {
        this.city = city;
        this.state = state;
    }
}

@Entity
@Table(name = "project")
class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String projectName;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Project() {}
    public Project(String projectName) {
        this.projectName = projectName;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}



public class HibernateDemo {

    public static void main(String[] args) {
    
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Employee.class);
        cfg.addAnnotatedClass(Address.class);
        cfg.addAnnotatedClass(Project.class);

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

  
        Employee emp = new Employee("Govinda Tak");

   
        Address addr = new Address("Pune", "Maharashtra");
        emp.setAddress(addr);

      
        Project p1 = new Project("AI/ML Project");
        Project p2 = new Project("Java Backend Project");
        emp.addProject(p1);
        emp.addProject(p2);

     
        session.save(emp);

        tx.commit();
        session.close();
        factory.close();

        System.out.println("Data saved successfully!");
    }
}
