package com.amf.crm.data.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amf.crm.data.entity.Client;
import com.amf.crm.data.repository.ClientRepository;
import com.amf.crm.data.repository.LoanRepository;

@Service 
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository,
                      LoanRepository companyRepository) { 
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return clientRepository.findAll();
        } else {
            return clientRepository.search(stringFilter);
        }
    }

    public long countClients() {
        return clientRepository.count();
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public void saveClient(Client client) {
        if (client == null) { 
            System.err.println("Client is null. Are you sure you have connected your form to the application?");
            return;
        }
        clientRepository.save(client);
    }
}