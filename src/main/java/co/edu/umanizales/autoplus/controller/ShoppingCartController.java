package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.ShoppingCart;
import co.edu.umanizales.autoplus.service.ShoppingCartService;
import co.edu.umanizales.autoplus.service.ClientService;
import co.edu.umanizales.autoplus.model.entities.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ClientService clientService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ClientService clientService) {
        this.shoppingCartService = shoppingCartService;
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> findById(@PathVariable String id) {
        ShoppingCart cart = shoppingCartService.findById(id);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<ShoppingCart> create(@RequestBody JsonNode body) {
        String clientId = null;
        if (body.has("client") && body.get("client").has("id")) {
            clientId = body.get("client").get("id").asText();
        } else if (body.has("clientId")) {
            clientId = body.get("clientId").asText();
        }
        if (clientId == null || clientId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "clientId es requerido");
        }
        Client client = clientService.findById(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado: " + clientId);
        }
        ShoppingCart cart = new ShoppingCart();
        cart.setClient(client);
        if (body.has("creationDate")) {
            cart.setCreationDate(body.get("creationDate").asText());
        }
        ShoppingCart created = shoppingCartService.create(cart);
        return ResponseEntity.created(URI.create("/api/carts/" + created.getId())).body(created);
    }

    @PostMapping(consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<ShoppingCart> createFromForm(@RequestParam String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "clientId es requerido");
        }
        Client client = clientService.findById(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado: " + clientId);
        }
        ShoppingCart cart = new ShoppingCart();
        cart.setClient(client);
        ShoppingCart created = shoppingCartService.create(cart);
        return ResponseEntity.created(URI.create("/api/carts/" + created.getId())).body(created);
    }

    @PostMapping(value = "/{id}/items", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<Void> addItem(@PathVariable String id,
                                        @RequestParam String accessoryId,
                                        @RequestParam int quantity) {
        shoppingCartService.addItem(id, accessoryId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/items/{accessoryId}")
    public ResponseEntity<Void> removeItem(@PathVariable String id, @PathVariable String accessoryId) {
        shoppingCartService.removeItem(id, accessoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/clear")
    public ResponseEntity<Void> clear(@PathVariable String id) {
        ShoppingCart existing = shoppingCartService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        shoppingCartService.clear(id);
        return ResponseEntity.noContent().build();
    }
}
