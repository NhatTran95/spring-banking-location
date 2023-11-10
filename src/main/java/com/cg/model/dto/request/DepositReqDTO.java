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
    private String idCustomer;
    private String amount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        DepositReqDTO depositReqDTO = (DepositReqDTO) o;
        BigDecimal transactionAmount = new BigDecimal(depositReqDTO.getAmount());

        if(transactionAmount == null || depositReqDTO.getAmount().isEmpty()) {
            errors.rejectValue("amount", "des.transactionAmount", "Vui lòng nhập số tiền muốn nạp");
            return;
        }
        if(transactionAmount.compareTo(BigDecimal.valueOf(100000)) < 0) {
            errors.rejectValue("amount", "des.transactionAmount.min", "Vui lòng nhập số tiền tối thiểu là 100.000");
            return;
        }

        if(transactionAmount.compareTo(BigDecimal.valueOf(500000000)) > 0) {
            errors.rejectValue("amount", "des.transactionAmount.max", "Vui lòng nhập số tiền tối đa 500.000.000");
        }

    }
}
