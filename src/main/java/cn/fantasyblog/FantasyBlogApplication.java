package cn.fantasyblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.logging.LoggingApplicationListener;

import java.util.Arrays;

@SpringBootApplication
public class FantasyBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasyBlogApplication.class, args); }

}
