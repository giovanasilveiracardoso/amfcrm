package com.amf.crm.data.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amf.crm.data.entity.Loan;
import com.amf.crm.data.repository.LoanRepository;

@Service 
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) { 
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAll(String stringFilter) {
    	return loanRepository.findAll();
    }

    public long countLoans() {
        return loanRepository.count();
    }

    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }

    public void saveLoan(Loan loan) {
        if (loan == null) { 
            System.err.println("Loan is null. Are you sure you have connected your form to the application?");
            return;
        }
        Double totalAmount = calculateTotalAmount(loan);
        loan.setTotalAmount(formatValue(totalAmount));
        Double installmentAmount = calculateInstallmentAmount(loan, totalAmount);
        loan.setInstallmentAmount(installmentAmount);
        loanRepository.save(loan);
    }

	private double calculateInstallmentAmount(Loan loan, Double totalAmount) {
		Double installmentAmount = totalAmount / loan.getInstallment();
		return formatValue(installmentAmount);
	}

	private double formatValue(Double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	private double calculateTotalAmount(Loan loan) {
		return loan.getAmount() * (loan.getInterestRate() / 100 + 1);
	}

}