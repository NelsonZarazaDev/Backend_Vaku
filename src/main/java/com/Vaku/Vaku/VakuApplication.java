package com.Vaku.Vaku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

//@SpringBootApplication
//public class VakuApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(VakuApplication.class, args);
//	}
//
//}

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VakuApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(VakuApplication.class, args);

        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        System.out.println(passwordEncoder.encode("123456"));
    }
}