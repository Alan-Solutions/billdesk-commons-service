package com.alan.billdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.alan.billdesk.repository","com.alan.user.dao"})
@ComponentScan({"com.alan.user", "com.alan.billdesk"})
@EntityScan({"com.alan.user.entity","com.alan.billdesk.entity"})
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }
}
