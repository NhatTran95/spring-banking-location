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
public class WithdrawReqDTO implements Validator {
    private String idCustomer;
    private String amount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        WithdrawReqDTO withdrawReqDTO = (WithdrawReqDTO) o;
        BigDecimal transactionAmount = new BigDecimal(withdrawReqDTO.getAmount());

        if(transactionAmount == null || withdrawReqDTO.getAmount().isEmpty()) {
            errors.rejectValue("amount", "wdr.transactionAmount", "Vui lòng nhập số tiền muốn rút");
            return;
        }
        if(transactionAmount.compareTo(BigDecimal.valueOf(10000)) < 0) {
            errors.rejectValue("amount", "wdr.transactionAmount.min", "Vui lòng nhập số tiền tối thiểu là 10.000");
            return;
        }

        if(transactionAmount.compareTo(BigDecimal.valueOf(100000000)) > 0) {
            errors.rejectValue("amount", "wdr.transactionAmount.max", "Vui lòng nhập số tiền tối đa là 100.000.000");
        }




    }
}
