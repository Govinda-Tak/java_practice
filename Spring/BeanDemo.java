
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component 
class Student {
    private String name;

    public Student() {
        this.name = "Govinda";
    }

    public void showInfo() {
        System.out.println("Student Bean Initialized with name = " + name);
    }


    @PostConstruct
    public void init() {
        System.out.println("Student bean init() called...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Student bean destroy() called...");
    }
}

@Configuration
@ComponentScan(basePackages = "your.package")
class AppConfig {

    @Bean
    public String course() {
        return "Spring Framework Learning";
    }
}

public class SpringBeanDemo {
    public static void main(String[] args) {
      
        ApplicationContext xmlContext =
                new ClassPathXmlApplicationContext("beans.xml");
        Student studentXml = (Student) xmlContext.getBean("studentBean");
        studentXml.showInfo();


        AnnotationConfigApplicationContext annotationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        Student studentAnno = annotationContext.getBean(Student.class);
        studentAnno.showInfo();

        String course = annotationContext.getBean(String.class);
        System.out.println("Course Bean from @Configuration: " + course);

 
        annotationContext.close();
        ((ClassPathXmlApplicationContext) xmlContext).close();
    }
}
