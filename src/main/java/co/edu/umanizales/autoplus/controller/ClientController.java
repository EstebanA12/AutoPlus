package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.entities.Client;
import co.edu.umanizales.autoplus.service.ClientService;
import java.util.List;

/**
 * REST Controller for managing clients
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        clientService.loadClients();
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        clientService.loadClients();
        Client client = clientService.getClientById(id);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        clientService.addClient(client);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
        client.setId(id);
        clientService.updateClient(client);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
