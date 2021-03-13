package com.zonions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.zonions")
@EnableJpaRepositories
@EnableTransactionManagement
public class ZonionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZonionsApplication.class, args);
  }

}
