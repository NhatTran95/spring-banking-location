package com.cg.service.withdraw;

import com.cg.model.Customer;
import com.cg.model.Withdraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WithdrawServiceImpl implements IWithdrawService {

    private static final List<Withdraw> withdraws = new ArrayList<>();

    private static Long id;

    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraw> findById(Long aLong) {
        return null;
    }

    @Override
    public void create(Withdraw withdraw) {
        withdraw.setId(id++);
        withdraws.add(withdraw);
    }

    @Override
    public void update(Long aLong, Withdraw withdraw) {

    }

    @Override
    public void removeById(Long aLong) {
    }
}
