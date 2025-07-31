import java.util.*;
import java.util.stream.Collectors;


class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

 
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "Employee{" +
               "ID=" + id +
               ", Name='" + name + '\'' +
               ", Department='" + department + '\'' +
               ", Salary=" + salary +
               '}';
    }
}

public class ArrayListDemo {
    public static void main(String[] args) {
        System.out.println("===== ArrayList Demo with Employee System =====\n");


        List<Employee> empList = new ArrayList<>();

       
        empList.add(new Employee(101, "Govinda", "IT", 75000));
        empList.add(new Employee(102, "Aniket", "HR", 50000));
        empList.add(new Employee(103, "Suresh", "Finance", 67000));
        empList.add(new Employee(104, "Meena", "IT", 72000));
        empList.add(new Employee(105, "Ravi", "Sales", 48000));

        System.out.println("Original Employee List:");
        empList.forEach(System.out::println);

        System.out.println("\nSorted by Salary (Descending):");
        empList.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        empList.forEach(System.out::println);

        for (Employee emp : empList) {
            if (emp.getDepartment().equalsIgnoreCase("IT")) {
                emp.setSalary(emp.getSalary() + 5000);
            }
        }
        System.out.println("\nAfter Salary Increment for IT Department:");
        empList.forEach(System.out::println);


        System.out.println("\nEmployees with Salary > 60000:");
        List<Employee> highEarners = empList.stream()
                .filter(e -> e.getSalary() > 60000)
                .collect(Collectors.toList());
        highEarners.forEach(System.out::println);

     
        int removeId = 102;
        empList.removeIf(e -> e.getId() == removeId);
        System.out.println("\nAfter Removing Employee with ID " + removeId + ":");
        empList.forEach(System.out::println);

        String searchName = "Ravi";
        System.out.println("\nSearching for employee with name " + searchName + ":");
        for (Employee emp : empList) {
            if (emp.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Found: " + emp);
                break;
            }
        }

       
    }
}
