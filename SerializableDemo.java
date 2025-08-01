import java.io.*;
import java.util.*;


enum Role implements Serializable {
    ADMIN("Admin has full access"),
    USER("User has limited access"),
    GUEST("Guest has read-only access");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

class Person implements Serializable {
    private String name;
    private Role role;

    public Person(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String toString() {
        return "Name: " + name + ", Role: " + role + " (" + role.getDescription() + ")";
    }
}


public class EnumSerializationWithCollections {
    public static void main(String[] args) {
        String filename = "people.ser";

      
        List<Person> people = new ArrayList<>();
        people.add(new Person("Govinda", Role.ADMIN));
        people.add(new Person("Ravi", Role.USER));
        people.add(new Person("Meena", Role.USER));
        people.add(new Person("Ajay", Role.GUEST));
        people.add(new Person("Seema", Role.ADMIN));

     
        Map<Role, List<Person>> roleMap = new HashMap<>();
        for (Person p : people) {
            roleMap.computeIfAbsent(p.getRole(), k -> new ArrayList<>()).add(p);
        }

     
        System.out.println("📊 People Grouped by Role:");
        for (Map.Entry<Role, List<Person>> entry : roleMap.entrySet()) {
            System.out.println("🔹 " + entry.getKey() + ":");
            for (Person p : entry.getValue()) {
                System.out.println("   👉 " + p);
            }
        }

     
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(people);
            System.out.println("\n People list serialized successfully.");
        } catch (IOException e) {
            System.out.println(" Serialization error: " + e.getMessage());
        }

     
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<Person> deserializedPeople = (List<Person>) in.readObject();
            System.out.println("\n People list deserialized successfully.");
            System.out.println("📦 Deserialized People:");
            deserializedPeople.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Deserialization error: " + e.getMessage());
        }
    }
}