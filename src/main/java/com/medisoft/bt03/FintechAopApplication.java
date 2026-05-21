package com.medisoft.bt03;

import com.fintech.aop.exception.OtpValidationException;
import com.fintech.aop.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FintechAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechAopApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(TransactionService service) {
        return args -> {
            System.out.println("\n========================================");
            System.out.println("   DEMO: BẢO MẬT OTP BẰNG CUSTOM AOP");
            System.out.println("========================================\n");

            // Test 1: Xem số dư — không cần OTP
            System.out.println("--- Test 1: Xem số dư (không cần OTP) ---");
            System.out.println(service.getBalance());

            // Test 2: Rút tiền với OTP hợp lệ
            System.out.println("\n--- Test 2: Rút tiền với OTP đúng (123456) ---");
            System.out.println(service.withdraw(500_000, "123456"));

            // Test 3: Chuyển khoản với OTP hợp lệ
            System.out.println("\n--- Test 3: Chuyển khoản với OTP đúng (123456) ---");
            System.out.println(service.transfer("nguyen.van.a@bank.com", 1_000_000, "123456"));

            // Test 4: Rút tiền với OTP rỗng — bẫy dữ liệu
            System.out.println("\n--- Test 4: Rút tiền với OTP rỗng (bẫy dữ liệu) ---");
            try {
                service.withdraw(500_000, "");
            } catch (OtpValidationException e) {
                System.out.println("❌ Exception: " + e.getMessage());
            }

            // Test 5: Rút tiền với OTP null — bẫy dữ liệu
            System.out.println("\n--- Test 5: Rút tiền với OTP null (bẫy dữ liệu) ---");
            try {
                service.withdraw(500_000, null);
            } catch (OtpValidationException e) {
                System.out.println("❌ Exception: " + e.getMessage());
            }

            // Test 6: Chuyển khoản với OTP sai
            System.out.println("\n--- Test 6: Chuyển khoản với OTP sai ---");
            try {
                service.transfer("hacker@evil.com", 999_999, "000000");
            } catch (OtpValidationException e) {
                System.out.println("❌ Exception: " + e.getMessage());
            }

            System.out.println("\n========================================");
        };
    }
}
