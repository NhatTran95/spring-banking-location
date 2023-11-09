package com.cg.controller;

import com.cg.model.*;
import com.cg.model.dto.response.CustomerResDTO;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.color.ICC_ColorSpace;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping
    public String showListPage(Model model) {
        return "customer/list";
    }

//    @GetMapping("/create")
//    public String showCreatePage(Model model) {
//        model.addAttribute("customer", new Customer());
//
//        return "customer/create";
//    }

//    @PostMapping("/create")
//    public String createCustomer(@ModelAttribute Customer customer, Model model) {
//
//        if (customer.getFullName().length() == 0) {
//            model.addAttribute("success", false);
//            model.addAttribute("message", "Created unsuccessful");
//        } else {
//            customerService.create(customer);
//            model.addAttribute("customer", new Customer());
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Created successfully");
//        }
//
//        return "customer/create";
//    }

//    @GetMapping("/edit/{id}")
//    public String showEditPage(Model model, @PathVariable Long id) {
//        Optional<Customer> customer = customerService.findById(id);
//
//        if (customer.isEmpty()) {
//            model.addAttribute("message", "Can't find any customer with that ID");
//            model.addAttribute("success", false);
//            model.addAttribute("customer", new Customer());
//        } else {
//            model.addAttribute("customer", customer);
//        }
//        return "customer/edit";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editCustomer(@ModelAttribute Customer customer, Model model, @PathVariable Long id) {
//        customerService.update(id, customer);
//        model.addAttribute("success", true);
//        model.addAttribute("message", "Edit successfully");
//        return "customer/edit";
//    }

//    @GetMapping("{id}")
//    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        List<Customer> customerDelete = customerService.findAll();
//        customerService.removeById(id);
//        redirectAttributes.addFlashAttribute("success", true);
//        redirectAttributes.addFlashAttribute("message", "Delete successfully");
//        redirectAttributes.addFlashAttribute("customers", customerDelete);
//
//        return "redirect:/customers";
//    }

//    @GetMapping("/deposit/{id}")
//    public String showDepositPage(Model model, @PathVariable Long id) {
//        Optional<Customer> customer = customerService.findById(id);
//
//        if (customer.isEmpty()) {
//            model.addAttribute("success", false);
//            model.addAttribute("message", "Can't find any customer with that ID");
//        } else {
//            Deposit deposit = new Deposit();
//            deposit.setCustomer(customer.get());
//            model.addAttribute("deposit", deposit);
//        }
//        return "customer/deposit";
//    }
//
//    @PostMapping("/deposit/{id}")
//    public String deposit(@ModelAttribute Deposit deposit, @PathVariable Long id, Model model) {
//
//        if (deposit.getTransactionAmount() == null || deposit.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
//            model.addAttribute("message", "Please input deposit bigger than 0");
//            model.addAttribute("success", false);
//            model.addAttribute("deposit", deposit);
//        } else {
//            customerService.deposit(deposit);
//            deposit.setTransactionAmount(null);
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Deposit completed");
//
//            Optional<Customer> customer = customerService.findById(id);
//            deposit.setCustomer(customer.get());
//        }
//
//        return "/customer/deposit";
//    }
//
//    @GetMapping("/withdraw/{id}")
//    public String showWithdrawPage(Model model, @PathVariable Long id) {
//
//        Optional<Customer> customer = customerService.findById(id);
//
//        if (customer == null) {
//            model.addAttribute("messageID", "Can't find any customer with that ID");
//            model.addAttribute("successWithdraw", false);
//            model.addAttribute("customer", new Customer());
//        } else {
//            Withdraw withdraw = new Withdraw();
//            withdraw.setCustomer(customer.get());
//            model.addAttribute("withdraw", withdraw);
//        }
//        return "customer/withdraw";
//    }
//
//    @PostMapping("/withdraw/{id}")
//    public String withdraw(@ModelAttribute Withdraw withdraw, RedirectAttributes redirectAttributes, @PathVariable Long id) {
//
//        if (withdraw.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0 || withdraw.getCustomer().getBalance().compareTo(withdraw.getTransactionAmount()) < 0) {
//            redirectAttributes.addFlashAttribute("message", "Please input withdraw bigger than 0 and not lower than current balance");
//            redirectAttributes.addFlashAttribute("success", false);
//            redirectAttributes.addFlashAttribute("withdraw", withdraw);
//        } else {
//            customerService.withdraw(withdraw);
//
//            withdraw.setTransactionAmount(null);
//            redirectAttributes.addFlashAttribute("withdraw", withdraw);
//            redirectAttributes.addFlashAttribute("success", true);
//            redirectAttributes.addFlashAttribute("message", "Withdraw completed");
//        }
//        return "redirect:/customers/withdraw/" + id;
//    }
//
//    @GetMapping("/transfer/{id}")
//    public String transfer(Model model, @PathVariable Long id) {
//        Optional<Customer> sender = customerService.findById(id);
//        List<Customer> recipients = customerService.findAllWithoutId(id);
//
//        if (sender.isEmpty()) {
//            model.addAttribute("success", false);
//            model.addAttribute("message", "Not found Sender");
//            model.addAttribute("transfer", new Transfer());
//            model.addAttribute("recipient", recipients);
//        } else {
//            Transfer transfer = new Transfer();
//            transfer.setSender(sender.get());
//            transfer.setFee(BigDecimal.valueOf(10));
//
//            model.addAttribute("transfer", transfer);
//            model.addAttribute("recipient", recipients);
//        }
//
//        return "/customer/transfer";
//    }
//
//    @PostMapping("/transfer/{id}")
//    public String transfer(@ModelAttribute Transfer transfer, RedirectAttributes redirectAttributes, @PathVariable Long id, Model model) {
//        Optional<Customer> sender = customerService.findById(id);
//        List<Customer> recipient = customerService.findAllWithoutId(id);
//
//        if (sender.isEmpty() || recipient.isEmpty()) {
//            model.addAttribute("success", false);
//            model.addAttribute("message", "Sender or Recipient not found");
//            model.addAttribute("transfer", new Transfer());
//            return "/customer/transfer";
//        }
//
//        BigDecimal transferAmount = transfer.getTransferAmount();
//        BigDecimal senderBalance = transfer.getSender().getBalance();
//
//        if ((transferAmount == null || transferAmount.compareTo(BigDecimal.ZERO) <= 0) || senderBalance.compareTo(transferAmount) <= 0) {
//            redirectAttributes.addFlashAttribute("success", false);
//            redirectAttributes.addFlashAttribute("message", "Please enter Transfer Amount, or higher than 0 and Sender current balance");
//        } else {
//            customerService.transfer(transfer);
//            transfer.setTransferAmount(null);
//            redirectAttributes.addFlashAttribute("success", true);
//            redirectAttributes.addFlashAttribute("message", "Transfer completed");
//            redirectAttributes.addFlashAttribute("transfer", transfer);
//
//        }
//        redirectAttributes.addFlashAttribute("recipient", recipient);
//        return "redirect:/customers/transfer/" + id;
//    }

    @GetMapping("/histories")
    public String transferHistories(Model model) {
        List<History> historylist = customerService.findAllHistory();

        model.addAttribute("historyList", historylist);

        return "customer/histories";
    }
}


















