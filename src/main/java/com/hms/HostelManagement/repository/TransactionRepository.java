package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate template;

    public void createTransaction(Transaction t){
        String sql = "insert into transaction(roll,hostel_registration_id,payment_id,signature,amount,currency,description) values (?, ?, ?,?,?,?,?)";
        template.update(sql,t.getRoll(),t.getHostelRegistrationId(),t.getPaymentId(),t.getSignature(),t.getAmount(),t.getCurrency(),t.getDescription());
    }
}
