
import jakarta.persistence.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;

@Entity
class Employee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
  String name, dept; double salary;
  Employee() {}
  Employee(String n,String d,double s){name=n;dept=d;salary=s;}
  public String toString(){return id+"-"+name+"-"+dept+"-"+salary;}
}

interface EmployeeRepo extends JpaRepository<Employee,Long> {
  List<Employee> findByDept(String dept);
  @Query("select e from Employee e where e.salary > :s") List<Employee> highEarners(double s);
}

@SpringBootApplication
public class JpaDemo {
  public static void main(String[] a){SpringApplication.run(JpaDemo.class,a);}
  @Bean CommandLineRunner run(EmployeeRepo repo){
    return args->{
      // Create
      repo.saveAll(Arrays.asList(
        new Employee("Govinda","IT",60000),
        new Employee("Aniket","HR",50000),
        new Employee("Rohit","IT",75000)
      ));
      // Read
      repo.findAll().forEach(System.out::println);
      // Derived query
      System.out.println("IT Dept: "+repo.findByDept("IT"));
      // Custom @Query
      System.out.println("High Earners: "+repo.highEarners(55000));
      // Pagination + Sorting
      Page<Employee> page = repo.findAll(PageRequest.of(0,2,Sort.by("salary").descending()));
      System.out.println("Paged: "+page.getContent());
      // Update
      Employee e=repo.findById(1L).get(); e.salary=70000; repo.save(e);
      // Delete
      repo.deleteById(2L);
      System.out.println("After Delete: "+repo.findAll());
    };
  }
}
