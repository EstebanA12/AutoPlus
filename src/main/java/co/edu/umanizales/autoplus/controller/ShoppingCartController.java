package co.edu.umanizales.autoplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.umanizales.autoplus.model.entities.ShoppingCart;
import co.edu.umanizales.autoplus.service.ShoppingCartService;
import java.util.List;

/**
 * REST Controller for managing shopping carts
 */
@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllCarts() {
        shoppingCartService.loadCarts();
        return ResponseEntity.ok(shoppingCartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCartById(@PathVariable String id) {
        shoppingCartService.loadCarts();
        ShoppingCart cart = shoppingCartService.getCartById(id);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart cart) {
        shoppingCartService.addCart(cart);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateCart(@PathVariable String id, @RequestBody ShoppingCart cart) {
        cart.setId(id);
        shoppingCartService.updateCart(cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        shoppingCartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
