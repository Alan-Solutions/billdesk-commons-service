package com.alan.billdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.alan.user")
@EntityScan("com.alan.user.entity")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }
}
