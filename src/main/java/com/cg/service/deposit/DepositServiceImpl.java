package com.cg.service.deposit;

import com.cg.model.Customer;
import com.cg.model.Deposit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepositServiceImpl implements IDepositService {

    private final static List<Deposit> deposits = new ArrayList<>();
    private static Long id;

    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long aLong) {
        return null;
    }

    @Override
    public void create(Deposit deposit) {
        deposit.setId(id++);
        deposits.add(deposit);
    }

    @Override
    public void update(Long aLong, Deposit deposit) {

    }

    @Override
    public void removeById(Long aLong) {

    }
}
