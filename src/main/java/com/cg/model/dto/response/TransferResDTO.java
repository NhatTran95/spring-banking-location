package com.cg.model.dto.response;
import com.cg.model.dto.response.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransferResDTO {
    private CustomerResDTO sender;
    private CustomerResDTO recipient;
}
