package co.edu.umanizales.autoplus.service;

import co.edu.umanizales.autoplus.model.entities.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final CsvService csvService;

    public ClientService(CsvService csvService) {
        this.csvService = csvService;
    }

    public Client findById(String id) {
        String line = csvService.readLine("clients.csv", id);
        if (line == null) return null;
        Client client = new Client();
        client.fromCsv(line);
        return client;
    }
}
