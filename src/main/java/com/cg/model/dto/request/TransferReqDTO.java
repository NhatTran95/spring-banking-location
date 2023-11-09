package com.cg.model.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransferReqDTO implements Validator {
    private String senderId;
    private String recipientId;
    private String transferAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        TransferReqDTO transferReqDTO = (TransferReqDTO) o;
        BigDecimal transferAmount = new BigDecimal(transferReqDTO.getTransferAmount());

        if(transferAmount == null || transferReqDTO.getTransferAmount().isEmpty()) {
            errors.rejectValue("transferAmount", "tr.transferAmount", "Vui lòng nhập số tiền muốn chuyển");
            return;
        }
        if(transferAmount.compareTo(BigDecimal.valueOf(1000)) < 0) {
            errors.rejectValue("transferAmount", "tr.transferAmount.min", "Vui lòng nhập số tiền lớn hơn 1.000");
            return;
        }

        if(transferAmount.compareTo(BigDecimal.valueOf(200000000)) > 0) {
            errors.rejectValue("transferAmount", "tr.transferAmount.max", "Vui lòng nhập số tiền nhỏ hơn 200.000.000");
        }

    }
}
