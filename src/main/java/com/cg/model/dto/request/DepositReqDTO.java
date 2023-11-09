package com.cg.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class DepositReqDTO implements Validator {
    private String customerId;
    private String transactionAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        DepositReqDTO depositReqDTO = (DepositReqDTO) o;
        BigDecimal transactionAmount = new BigDecimal(depositReqDTO.getTransactionAmount());

        if(transactionAmount == null || depositReqDTO.getTransactionAmount().isEmpty()) {
            errors.rejectValue("transactionAmount", "des.transactionAmount", "Vui lòng nhập số tiền muốn nạp");
            return;
        }
        if(transactionAmount.compareTo(BigDecimal.valueOf(1000)) < 0) {
            errors.rejectValue("transactionAmount", "des.transactionAmount.min", "Vui lòng nhập số tiền lớn hơn 1.000");
            return;
        }

        if(transactionAmount.compareTo(BigDecimal.valueOf(500000000)) > 0) {
            errors.rejectValue("transactionAmount", "des.transactionAmount.max", "Vui lòng nhập số tiền nhỏ hơn 500.000.000");
        }

    }
}
