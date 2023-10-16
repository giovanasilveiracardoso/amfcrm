package com.amf.crm.data.entity;

import java.util.LinkedList;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Client extends AbstractEntity {

    @NotEmpty
    private String name = "";

    @NotEmpty
    private String cpf = "";

    @OneToMany(mappedBy = "client")
    @Nullable
    private List<Loan> loans = new LinkedList<>();

    @Override
    public String toString() {
        return name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
    
}
