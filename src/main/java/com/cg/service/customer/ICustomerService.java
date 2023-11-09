package com.cg.service.customer;

import com.cg.model.*;
import com.cg.model.dto.request.TransferReqDTO;
import com.cg.model.dto.response.CustomerResDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {

    List<CustomerResDTO> findAllCustomerResDTO();

    void deposit (Deposit deposit);

    void withdraw (Withdraw withdraw);

    void transfer (TransferReqDTO transferReqDTO);

    List<History> findAllHistory();

    List<Customer> findAllWithoutId(Long id);

    // Call API
    Customer createCustomer (Customer customer);

    Customer update(Customer customer);


}
