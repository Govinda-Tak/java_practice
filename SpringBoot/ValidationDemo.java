
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.*;

@SpringBootApplication
public class ValidationExceptionDemo {
    public static void main(String[] args) {
        SpringApplication.run(ValidationExceptionDemo.class, args);
    }
}


class UserDto {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

  
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}


@RestController
@RequestMapping("/api/users")
class UserController {
    private List<UserDto> users = new ArrayList<>();

   
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

   
    @GetMapping
    public List<UserDto> getAllUsers(){
        return users;
    }
}


@ControllerAdvice
class GlobalExceptionHandler {

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

  
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
