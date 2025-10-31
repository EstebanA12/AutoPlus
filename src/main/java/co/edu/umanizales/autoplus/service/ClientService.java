package co.edu.umanizales.autoplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.umanizales.autoplus.model.entities.Client;
import java.util.*;

/**
 * Service for managing clients
 */
@Service
public class ClientService {
    @Autowired
    private CsvService csvService;

    private static final String CLIENTS_FILE = "clients.csv";
    private List<Client> clients = new ArrayList<>();

    public void loadClients() {
        clients.clear();
        clients.addAll(csvService.loadFromCSV(CLIENTS_FILE, Client.class));
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public Client getClientById(String id) {
        return clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addClient(Client client) {
        clients.add(client);
        csvService.appendToCSV(client, CLIENTS_FILE);
    }

    public void updateClient(Client client) {
        clients.removeIf(c -> c.getId().equals(client.getId()));
        clients.add(client);
        saveAll();
    }

    public void deleteClient(String id) {
        clients.removeIf(c -> c.getId().equals(id));
        saveAll();
    }

    private void saveAll() {
        csvService.saveToCSV(clients, CLIENTS_FILE);
    }
}
