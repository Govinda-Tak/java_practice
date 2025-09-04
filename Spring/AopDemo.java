

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@Component
class PaymentService {
    public void makePayment() {
        System.out.println(">> Payment of $100 processed successfully!");
    }

    public void refundPayment() {
        System.out.println(">> Payment refunded successfully!");
    }
}


@Aspect
@Component
class LoggingAspect {


    @Before("execution(* PaymentService.*(..))")
    public void logBefore(JoinPoint jp) {
        System.out.println("[LOG] Method called: " + jp.getSignature().getName());
    }
}


@Configuration
@ComponentScan(basePackages = "demo.package") 
@EnableAspectJAutoProxy
class AppConfig { }


public class SpringAOPDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        PaymentService ps = context.getBean(PaymentService.class);

        ps.makePayment();
        ps.refundPayment();

        context.close();
    }
}
