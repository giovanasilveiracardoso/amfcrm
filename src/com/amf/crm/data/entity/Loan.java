package com.amf.crm.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Loan extends AbstractEntity {

	@NotNull
    private Double amount;
	
	@Column(name = "installment_amount")
	@NotNull
	private Double installmentAmount;
	
	@Column(name = "total_amount")
	@NotNull
	private Double totalAmount;

	@Column(name = "interest_rate")
    @NotNull
    private Double interestRate;
    
    @NotNull
    private Integer installment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

    public Double getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(Double installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getInstallment() {
		return installment;
	}

	public void setInstallment(Integer installment) {
		this.installment = installment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

//    @Formula("(select count(c.id) from Contact c where c.company_id = id)")
//    private int employeeCount;
//
//    public int getEmployeeCount(){
//        return employeeCount;
//    }
    
    
}
