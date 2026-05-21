package com.medisoft.bt03.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation đánh dấu các hàm giao dịch nhạy cảm BẮT BUỘC xác thực OTP.
 *
 * <p>Bất kỳ method nào được đánh dấu với {@code @RequiresOTP} sẽ bị Aspect
 * {@link com.fintech.aspect.OtpSecurityAspect} chặn lại để kiểm tra OTP
 * trước khi thực thi logic kinh doanh.</p>
 *
 * <p>Quy ước: Method được đánh dấu PHẢI có tham số kiểu {@code String}
 * tên là {@code otp} ở bất kỳ vị trí nào trong danh sách tham số.</p>
 *
 * <p>Ví dụ sử dụng:</p>
 * <pre>{@code
 * @RequiresOTP
 * public String withdraw(double amount, String otp) {
 *     return "Rút tiền thành công";
 * }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresOTP {
}
