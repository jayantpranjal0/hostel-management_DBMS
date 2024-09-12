package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Transaction;
import com.hms.HostelManagement.repository.TransactionRepository;
import com.hms.HostelManagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void createTransaction(Transaction t) {
        transactionRepository.createTransaction(t);
    }
}
