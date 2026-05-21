package com.medisoft.bt03.service;

import com.medisoft.bt03.annotation.RequiresOTP;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {


    @RequiresOTP
    public String withdraw(double amount, String otp) {
        // Chỉ chứa logic kinh doanh thuần túy
        return String.format("Rút tiền thành công: -%.0f VND", amount);
    }


    @RequiresOTP
    public String transfer(String toUser, double amount, String otp) {
        // Chỉ chứa logic kinh doanh thuần túy
        return String.format(" Chuyển khoản thành công: %.0f VND → %s", amount, toUser);
    }

    public String getBalance() {
        return " Số dư hiện tại: 2,000,000 VND";
    }
}
