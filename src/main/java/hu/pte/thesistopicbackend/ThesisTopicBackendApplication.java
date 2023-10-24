package hu.pte.thesistopicbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hu.pte")
public class ThesisTopicBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThesisTopicBackendApplication.class, args);
    }

}
