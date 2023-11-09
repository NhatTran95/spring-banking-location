package com.cg.service.customer;
import com.cg.model.*;
import com.cg.model.dto.request.TransferReqDTO;
import com.cg.model.dto.response.CustomerResDTO;
import com.cg.repository.*;
import com.cg.service.withdraw.IWithdrawService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    private final IDepositRepository depositRepository;

    private final IWithdrawRepository withdrawRepository;

    private final ITransferRepository transferRepository;

    private final IHistoryRepository historyRepository;

    private final ILocationRegionRepository locationRegionRepository;

    @Override
    public List<CustomerResDTO> findAllCustomerResDTO() {
        return customerRepository.findAllCustomerResDTO();
    }
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
    @Override
    public void create(Customer customer) {
        customer.setBalance(BigDecimal.ZERO);
        customerRepository.save(customer);
    }
    @Override
    public Customer createCustomer (Customer customer) {
        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

//        customer.setLocationRegion(locationRegion);
        customer.setBalance(BigDecimal.ZERO);
        customerRepository.save(customer);

        return customer;
    }
    @Override
    public void update(Long id, Customer customer) {
        Customer oldCustomer = findById(customer.getId()).get();
        customer.setBalance(oldCustomer.getBalance());
        customer.setDeleted(oldCustomer.getDeleted());
        customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer oldCus = findById(customer.getId()).get();
        customer.setBalance(oldCus.getBalance());
        customer.setDeleted(oldCus.getDeleted());
        locationRegionRepository.save(customer.getLocationRegion());
        customerRepository.save(customer);

        return customer;
    }

    @Override
    public void removeById(Long id) {
        customerRepository.deleteById(id);
    }
    @Override
    public void deposit(Deposit deposit) {
        depositRepository.save(deposit);

        Long customerId = deposit.getCustomer().getId();
        BigDecimal transactionAmount = deposit.getTransactionAmount();

        customerRepository.incrementBalance(customerId, transactionAmount);
    }
    @Override
    public void withdraw(Withdraw withdraw) {
        withdrawRepository.save(withdraw);

        Long customerId = withdraw.getCustomer().getId();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();

        customerRepository.decrementBalance(customerId, transactionAmount);
    }
    @Override
    public void transfer(TransferReqDTO transferReqDTO) {

        Long senderId = Long.valueOf(transferReqDTO.getSenderId());
        Long recipientId = Long.valueOf(transferReqDTO.getRecipientId());
        String transferAmountStr = transferReqDTO.getTransferAmount();
        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(transferAmountStr));
        Long fee = 10L;

        BigDecimal feeAmount = transferAmount.multiply(BigDecimal.valueOf(fee)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferAmount.add(feeAmount);

        customerRepository.decrementBalance(senderId, transactionAmount);
        customerRepository.incrementBalance(recipientId, transferAmount);

        Optional<Customer> sender = customerRepository.findById(senderId);
        Optional<Customer> recipient = customerRepository.findById(recipientId);

        Transfer transfer = new Transfer(transferAmount, transactionAmount, BigDecimal.valueOf(fee), sender.get(), recipient.get());

        transferRepository.save(transfer);

        createHistories(transfer);

    }
    public void createHistories(Transfer transfer) {
        History history = new History();

        history.setSender(transfer.getSender());
        history.setRecipient(transfer.getRecipient());
        history.setTransactionAmount(transfer.getTotal());
        history.setFee(transfer.getFee());
        history.setTransferAmount(transfer.getTransferAmount());
        history.setTransactionDate(new Date());

        historyRepository.save(history);
    }
    @Override
    public List<History> findAllHistory() {
        List<History> histories = historyRepository.findAll();
        return histories;
    }
    @Override
    public List<Customer> findAllWithoutId(Long id) {
        List<Customer> customers = customerRepository.findAllByDeleted(false);
        return customers.stream().filter(customer -> !customer.getId().equals(id)).collect(Collectors.toList());
    }

}
