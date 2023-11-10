package com.cg.controller.restController;

import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.model.dto.request.DepositReqDTO;
import com.cg.model.dto.request.TransferReqDTO;
import com.cg.model.dto.request.WithdrawReqDTO;
import com.cg.model.dto.response.CustomerResDTO;
import com.cg.model.dto.response.TransferResDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerRestController {

    private ICustomerService customerService;

    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTO();
        return new ResponseEntity<>(customerResDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult bindingResult) {
        new Customer().validate(customer, bindingResult);
        if(bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Customer newCustomer = customerService.createCustomer(customer);
        CustomerResDTO newCustomerResDTO = newCustomer.toCustomerResDTO();
        return new ResponseEntity<>(newCustomerResDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResDTO> getById(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElseThrow(() -> {
            throw new DataInputException("không tìm thấy thông tin khách hàng");
        });

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable Long id, BindingResult bindingResult) {
        new Customer().validate(customer, bindingResult);
        Optional<Customer> customerOld = customerService.findById(id);
        if (customerOld.isEmpty()) {
            FieldError fieldError = new FieldError("customer", "id", "không tìm thấy thông tin khách hàng");
            bindingResult.addError(fieldError);
            return appUtils.mapErrorToResponse(bindingResult);
        }else if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Customer customerUpdate = customerService.update(customer);

        return new ResponseEntity<>(customerUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        customerService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositReqDTO depositReqDTO, BindingResult bindingResult) {
        new DepositReqDTO().validate(depositReqDTO, bindingResult);

        Optional<Customer> customer = customerService.findById(Long.valueOf(depositReqDTO.getIdCustomer()));
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(String.valueOf(depositReqDTO.getAmount())));

        if (customer.isEmpty()) {
            FieldError fieldError = new FieldError("depositReqDTO", "idCustomer", "Không tìm thấy ID người dùng");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Deposit deposit = new Deposit();
        deposit.setCustomer(customer.get());
        deposit.setTransactionAmount(transactionAmount);

        customerService.deposit(deposit);
        Optional<Customer> updateCustomer = customerService.findById(deposit.getCustomer().getId());

        return new ResponseEntity<>(updateCustomer.get().toCustomerResDTO(), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawReqDTO withdrawReqDTO, BindingResult bindingResult) {
        new WithdrawReqDTO().validate(withdrawReqDTO, bindingResult);

        Optional<Customer> customer = customerService.findById(Long.valueOf(withdrawReqDTO.getIdCustomer()));
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(withdrawReqDTO.getAmount()));


        if (customer.isEmpty()) {
            FieldError fieldError = new FieldError("withdrawReqDTO", "idCustomer", "Không tìm thấy ID người dùng");
            bindingResult.addError(fieldError);
        }else if (transactionAmount.compareTo(customer.get().getBalance()) > 0) {
            FieldError error = new FieldError("withdrawReqDTO", "amount", "Số dư không đủ để rút");
            bindingResult.addError(error);
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer.get());
        withdraw.setTransactionAmount(transactionAmount);

        customerService.withdraw(withdraw);
        Optional<Customer> updateCustomer = customerService.findById(withdraw.getCustomer().getId());

        return new ResponseEntity<>(updateCustomer.get().toCustomerResDTO(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferReqDTO transferReqDTO, BindingResult bindingResult) {
        new TransferReqDTO().validate(transferReqDTO, bindingResult);

        Optional<Customer> senderOptional = customerService.findById(Long.parseLong(transferReqDTO.getSenderId()));
        Optional<Customer> recipientOptional = customerService.findById(Long.parseLong(transferReqDTO.getRecipientId()));

        if(transferReqDTO.getSenderId().equals(transferReqDTO.getRecipientId())) {
            FieldError fieldError = new FieldError("transferReqDTO", "recipientId", "ID người nhận trùng người gửi");
            bindingResult.addError(fieldError);
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (recipientOptional.isEmpty()) {
            FieldError fieldError = new FieldError("transferReqDTO", "recipientId", "Không tìm thấy ID người nhận");
            bindingResult.addError(fieldError);
        }

        BigDecimal transferAmount = new BigDecimal(transferReqDTO.getTransferAmount());
        Long fee = 10L;
        BigDecimal feeAmount = transferAmount.multiply(BigDecimal.valueOf(fee)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferAmount.add(feeAmount);

        if (senderOptional.isEmpty()) {
            FieldError fieldError = new FieldError("transferReqDTO", "senderId", "Không tìm thấy ID người gửi");
            bindingResult.addError(fieldError);
        }else if (transactionAmount.compareTo(senderOptional.get().getBalance()) > 0) {
            FieldError fieldError = new FieldError("transferReqDTO", "transferAmount", "Số tiền trong tài khoản không đủ để thực hiện giao dịch");
            bindingResult.addError(fieldError);
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customerService.transfer(transferReqDTO);

        TransferResDTO transferResDTO = new TransferResDTO();
        Optional<Customer> senderOptionalNew = customerService.findById(Long.parseLong(transferReqDTO.getSenderId()));
        Optional<Customer> recipientOptionalNew = customerService.findById(Long.parseLong(transferReqDTO.getRecipientId()));

        CustomerResDTO sender = senderOptionalNew.get().toCustomerResDTO();
        CustomerResDTO recipient = recipientOptionalNew.get().toCustomerResDTO();

        transferResDTO.setSender(sender);
        transferResDTO.setRecipient(recipient);

        return new ResponseEntity<>(transferResDTO, HttpStatus.OK);
    }
}
