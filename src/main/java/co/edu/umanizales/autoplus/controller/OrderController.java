package co.edu.umanizales.autoplus.controller;

import co.edu.umanizales.autoplus.model.entities.Order;
import co.edu.umanizales.autoplus.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import co.edu.umanizales.autoplus.service.ProviderService;
import co.edu.umanizales.autoplus.service.AccessoryService;
import co.edu.umanizales.autoplus.model.abstracts.Provider;
import co.edu.umanizales.autoplus.model.abstracts.Accessory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProviderService providerService;
    private final AccessoryService accessoryService;

    public OrderController(OrderService orderService, ProviderService providerService, AccessoryService accessoryService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.accessoryService = accessoryService;
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable String id) {
        Order order = orderService.findById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Order> create(@RequestBody JsonNode body) {
        String providerId = null;
        if (body.has("provider") && body.get("provider").has("id")) {
            providerId = body.get("provider").get("id").asText();
        } else if (body.has("providerId")) {
            providerId = body.get("providerId").asText();
        }
        String accessoryId = null;
        if (body.has("accessory") && body.get("accessory").has("id")) {
            accessoryId = body.get("accessory").get("id").asText();
        } else if (body.has("accessoryId")) {
            accessoryId = body.get("accessoryId").asText();
        }
        if (providerId == null || providerId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "providerId es requerido");
        }
        if (accessoryId == null || accessoryId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accessoryId es requerido");
        }

        Provider provider = providerService.findById(providerId);
        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor no encontrado: " + providerId);
        }
        Accessory accessory = accessoryService.findById(accessoryId);
        if (accessory == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accesorio no encontrado: " + accessoryId);
        }

        int quantity = body.has("quantity") ? body.get("quantity").asInt() : 1;
        if (quantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity debe ser >= 1");
        }
        double totalCost = body.has("totalCost") ? body.get("totalCost").asDouble() : accessory.getPrice() * quantity;

        Order order = new Order();
        order.setProvider(provider);
        order.setAccessory(accessory);
        order.setQuantity(quantity);
        order.setTotalCost(totalCost);
        if (body.has("orderDate")) order.setOrderDate(body.get("orderDate").asText());
        if (body.has("status")) order.setStatus(body.get("status").asText());

        Order created = orderService.create(order);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody JsonNode body) {
        Order existing = orderService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (body.has("provider") && body.get("provider").has("id")) {
            String providerId = body.get("provider").get("id").asText();
            Provider provider = providerService.findById(providerId);
            if (provider == null) return ResponseEntity.badRequest().build();
            existing.setProvider(provider);
        } else if (body.has("providerId")) {
            String providerId = body.get("providerId").asText();
            Provider provider = providerService.findById(providerId);
            if (provider == null) return ResponseEntity.badRequest().build();
            existing.setProvider(provider);
        }
        if (body.has("accessory") && body.get("accessory").has("id")) {
            String accessoryId = body.get("accessory").get("id").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        } else if (body.has("accessoryId")) {
            String accessoryId = body.get("accessoryId").asText();
            Accessory accessory = accessoryService.findById(accessoryId);
            if (accessory == null) return ResponseEntity.badRequest().build();
            existing.setAccessory(accessory);
        }
        if (body.has("quantity")) existing.setQuantity(body.get("quantity").asInt());
        if (body.has("totalCost")) existing.setTotalCost(body.get("totalCost").asDouble());
        if (body.has("orderDate")) existing.setOrderDate(body.get("orderDate").asText());
        if (body.has("status")) existing.setStatus(body.get("status").asText());

        Order updated = orderService.update(existing);
        return ResponseEntity.ok(updated);
    }
}
