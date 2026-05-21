package com.medisoft.bt03.aspect;

import com.medisoft.bt03.exception.OtpValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class OtpSecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(OtpSecurityAspect.class);

    // OTP mô phỏng hợp lệ cho mục đích demo
    private static final String VALID_OTP = "123456";


    @Around("@annotation(com.fintech.aop.annotation.RequiresOTP)")
    public Object enforceOtpSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[OTP Security] Đang kiểm tra OTP cho hàm: {}", methodName);

        // Lấy giá trị OTP từ tham số của hàm
        String otp = extractOtpFromArgs(joinPoint);

        // Bẫy dữ liệu: OTP null hoặc rỗng
        if (otp == null || otp.isBlank()) {
            log.warn("[OTP Security] BLOCKED: OTP trống/null tại hàm '{}'", methodName);
            throw new OtpValidationException(
                    "Giao dịch bị từ chối: OTP không được để trống. Vui lòng nhập mã OTP.");
        }

        // Xác thực OTP với hệ thống
        if (!verifyOtp(otp)) {
            log.warn("[OTP Security] BLOCKED: OTP sai '{}' tại hàm '{}'", otp, methodName);
            throw new OtpValidationException(
                    "Giao dịch bị từ chối: Mã OTP không hợp lệ.");
        }

        log.info("[OTP Security] PASSED: OTP hợp lệ, tiếp tục thực thi '{}'", methodName);
        // OTP hợp lệ → tiếp tục thực thi hàm gốc
        return joinPoint.proceed();
    }

    private String extractOtpFromArgs(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            if ("otp".equals(parameters[i].getName()) && args[i] instanceof String) {
                return (String) args[i];
            }
        }


        for (int i = args.length - 1; i >= 0; i--) {
            if (args[i] instanceof String) {
                return (String) args[i];
            }
        }

        return null;
    }

    private boolean verifyOtp(String otp) {
        return VALID_OTP.equals(otp);
    }
}
