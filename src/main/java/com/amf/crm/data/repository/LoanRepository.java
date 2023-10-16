package com.amf.crm.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amf.crm.data.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
