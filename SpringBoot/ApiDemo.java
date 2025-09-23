
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
public class ApiDevelopmentDemo {
    public static void main(String[] args) {
        SpringApplication.run(ApiDevelopmentDemo.class, args);
    }
}


class User {
    private int id; private String name; private String email;
    public User(int id, String name, String email){this.id=id;this.name=name;this.email=email;}
    public int getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
}


@RestController
@RequestMapping("/api/users")
class UserController {
    private List<User> users = new ArrayList<>(
        Arrays.asList(new User(1,"Govinda","govinda@example.com"), new User(2,"Aniket","aniket@example.com"))
    );

   
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

 
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return users.stream().filter(u->u.getId()==id).findFirst().orElse(null);
    }

  
    @PostMapping
    public String createUser(@RequestBody User user) {
        users.add(user);
        return "User added: " + user.getName();
    }

  
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        users.removeIf(u -> u.getId()==id);
        return "User deleted with id: " + id;
    }
}
