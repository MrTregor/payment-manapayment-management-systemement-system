package com.example.paymentmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Добавьте эту аннотацию
public class PaymentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentManagementSystemApplication.class, args);
		System.out.println("Payment Management System is running...");
	}
}
