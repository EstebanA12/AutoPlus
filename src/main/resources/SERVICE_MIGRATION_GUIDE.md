# Guía de Migración de Servicios

## Cambios Necesarios en la Capa de Servicios

Con el modelo refactorizado, los servicios deben ser actualizados para resolver relaciones entre objetos.

---

## 1. Patrón General de Migración

### Antes (Modelo Antiguo)
```java
@Service
public class SaleService {
    public Sale findById(String id) {
        String csvLine = csvService.readLine("sales.csv", id);
        Sale sale = new Sale();
        sale.fromCsv(csvLine);
        return sale;  // Solo contiene IDs
    }
}
```

### Después (Modelo Refactorizado)
```java
@Service
public class SaleService {
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private SellerService sellerService;
    
    public Sale findById(String id) {
        String csvLine = csvService.readLine("sales.csv", id);
        Sale sale = new Sale();
        sale.fromCsv(csvLine);
        
        // Resolver relaciones
        String clientId = extractClientIdFromCsv(csvLine);
        String sellerId = extractSellerIdFromCsv(csvLine);
        
        Client client = clientService.findById(clientId);
        Seller seller = sellerService.findById(sellerId);
        
        sale.setClient(client);
        sale.setSeller(seller);
        
        return sale;  // Contiene objetos completos
    }
}
```

---

## 2. Migración de OrderService

### Cambios Requeridos

```java
@Service
public class OrderService {
    @Autowired
    private ProviderService providerService;
    
    @Autowired
    private AccessoryService accessoryService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar pedido por ID y resolver relaciones
     */
    public Order findById(String id) {
        String csvLine = csvService.readLine("orders.csv", id);
        Order order = new Order();
        order.fromCsv(csvLine);
        
        // Extraer IDs del CSV
        String[] parts = csvLine.split(",");
        String providerId = parts[1];
        String accessoryId = parts[2];
        
        // Resolver relaciones
        Provider provider = providerService.findById(providerId);
        Accessory accessory = accessoryService.findById(accessoryId);
        
        order.setProvider(provider);
        order.setAccessory(accessory);
        
        return order;
    }
    
    /**
     * Crear nuevo pedido
     */
    public Order create(Order order) {
        if (order.getProvider() == null || order.getAccessory() == null) {
            throw new IllegalArgumentException("Provider y Accessory son requeridos");
        }
        
        order.setId(generateId());
        csvService.writeLine("orders.csv", order.toCsv());
        return order;
    }
    
    /**
     * Actualizar pedido
     */
    public Order update(Order order) {
        csvService.updateLine("orders.csv", order.getId(), order.toCsv());
        return order;
    }
    
    /**
     * Obtener todos los pedidos
     */
    public List<Order> findAll() {
        List<String> csvLines = csvService.readAll("orders.csv");
        return csvLines.stream()
                .map(line -> {
                    Order order = new Order();
                    order.fromCsv(line);
                    
                    String[] parts = line.split(",");
                    Provider provider = providerService.findById(parts[1]);
                    Accessory accessory = accessoryService.findById(parts[2]);
                    
                    order.setProvider(provider);
                    order.setAccessory(accessory);
                    
                    return order;
                })
                .collect(Collectors.toList());
    }
}
```

---

## 3. Migración de SaleService

### Cambios Requeridos

```java
@Service
public class SaleService {
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private AccessoryService accessoryService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar venta por ID y resolver relaciones
     */
    public Sale findById(String id) {
        String csvLine = csvService.readLine("sales.csv", id);
        Sale sale = new Sale();
        sale.fromCsv(csvLine);
        
        // Extraer IDs del CSV
        String[] parts = csvLine.split(",");
        String clientId = parts[1];
        String sellerId = parts[2];
        
        // Resolver relaciones
        Client client = clientService.findById(clientId);
        Seller seller = sellerService.findById(sellerId);
        
        sale.setClient(client);
        sale.setSeller(seller);
        
        // Cargar items de la venta
        List<SaleItem> items = loadSaleItems(id);
        sale.setItems(items);
        
        return sale;
    }
    
    /**
     * Cargar items de una venta
     */
    private List<SaleItem> loadSaleItems(String saleId) {
        List<String> itemLines = csvService.readLines("sale_items.csv", saleId);
        return itemLines.stream()
                .map(line -> {
                    SaleItem item = new SaleItem();
                    item.fromCsv(line);
                    
                    String[] parts = line.split(",");
                    String accessoryId = parts[1];
                    Accessory accessory = accessoryService.findById(accessoryId);
                    
                    item.setAccessory(accessory);
                    return item;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Crear nueva venta
     */
    public Sale create(Sale sale) {
        if (sale.getClient() == null || sale.getSeller() == null) {
            throw new IllegalArgumentException("Client y Seller son requeridos");
        }
        
        if (sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un item");
        }
        
        sale.setId(generateId());
        
        // Guardar venta
        csvService.writeLine("sales.csv", sale.toCsv());
        
        // Guardar items
        for (SaleItem item : sale.getItems()) {
            item.setId(generateItemId());
            csvService.writeLine("sale_items.csv", 
                    sale.getId() + "," + item.toCsv());
        }
        
        return sale;
    }
    
    /**
     * Actualizar venta
     */
    public Sale update(Sale sale) {
        csvService.updateLine("sales.csv", sale.getId(), sale.toCsv());
        
        // Actualizar items
        csvService.deleteLines("sale_items.csv", sale.getId());
        for (SaleItem item : sale.getItems()) {
            csvService.writeLine("sale_items.csv", 
                    sale.getId() + "," + item.toCsv());
        }
        
        return sale;
    }
    
    /**
     * Obtener todas las ventas
     */
    public List<Sale> findAll() {
        List<String> csvLines = csvService.readAll("sales.csv");
        return csvLines.stream()
                .map(line -> {
                    Sale sale = new Sale();
                    sale.fromCsv(line);
                    
                    String[] parts = line.split(",");
                    Client client = clientService.findById(parts[1]);
                    Seller seller = sellerService.findById(parts[2]);
                    
                    sale.setClient(client);
                    sale.setSeller(seller);
                    
                    List<SaleItem> items = loadSaleItems(sale.getId());
                    sale.setItems(items);
                    
                    return sale;
                })
                .collect(Collectors.toList());
    }
}
```

---

## 4. Migración de ShoppingCartService

### Cambios Requeridos

```java
@Service
public class ShoppingCartService {
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private AccessoryService accessoryService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar carrito por ID y resolver relaciones
     */
    public ShoppingCart findById(String id) {
        String csvLine = csvService.readLine("shopping_carts.csv", id);
        ShoppingCart cart = new ShoppingCart();
        cart.fromCsv(csvLine);
        
        // Extraer ID del cliente
        String[] parts = csvLine.split(",");
        String clientId = parts[1];
        
        // Resolver relación con cliente
        Client client = clientService.findById(clientId);
        cart.setClient(client);
        
        // Cargar items del carrito
        Map<Accessory, Integer> items = loadCartItems(id);
        cart.setItems(items);
        
        return cart;
    }
    
    /**
     * Cargar items de un carrito
     */
    private Map<Accessory, Integer> loadCartItems(String cartId) {
        List<String> itemLines = csvService.readLines("cart_items.csv", cartId);
        Map<Accessory, Integer> items = new HashMap<>();
        
        for (String line : itemLines) {
            String[] parts = line.split(",");
            String accessoryId = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            
            Accessory accessory = accessoryService.findById(accessoryId);
            items.put(accessory, quantity);
        }
        
        return items;
    }
    
    /**
     * Crear nuevo carrito
     */
    public ShoppingCart create(ShoppingCart cart) {
        if (cart.getClient() == null) {
            throw new IllegalArgumentException("Client es requerido");
        }
        
        cart.setId(generateId());
        csvService.writeLine("shopping_carts.csv", cart.toCsv());
        
        return cart;
    }
    
    /**
     * Agregar item al carrito
     */
    public void addItem(String cartId, Accessory accessory, int quantity) {
        ShoppingCart cart = findById(cartId);
        cart.addItem(accessory, quantity);
        
        // Actualizar CSV
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        
        // Guardar item
        csvService.writeLine("cart_items.csv", 
                cartId + "," + accessory.getId() + "," + quantity);
    }
    
    /**
     * Eliminar item del carrito
     */
    public void removeItem(String cartId, String accessoryId) {
        ShoppingCart cart = findById(cartId);
        
        Accessory accessory = accessoryService.findById(accessoryId);
        cart.removeItem(accessory);
        
        // Actualizar CSV
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        csvService.deleteLine("cart_items.csv", cartId + "," + accessoryId);
    }
    
    /**
     * Limpiar carrito
     */
    public void clear(String cartId) {
        ShoppingCart cart = findById(cartId);
        cart.clear();
        
        csvService.updateLine("shopping_carts.csv", cartId, cart.toCsv());
        csvService.deleteLines("cart_items.csv", cartId);
    }
}
```

---

## 5. Migración de WarrantyService

### Cambios Requeridos

```java
@Service
public class WarrantyService {
    @Autowired
    private AccessoryService accessoryService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar garantía por ID y resolver relaciones
     */
    public Warranty findById(String id) {
        String csvLine = csvService.readLine("warranties.csv", id);
        Warranty warranty = new Warranty();
        warranty.fromCsv(csvLine);
        
        // Extraer ID del accesorio
        String[] parts = csvLine.split(",");
        String accessoryId = parts[1];
        
        // Resolver relación con accesorio
        Accessory accessory = accessoryService.findById(accessoryId);
        warranty.setAccessory(accessory);
        
        return warranty;
    }
    
    /**
     * Crear nueva garantía
     */
    public Warranty create(Warranty warranty) {
        if (warranty.getAccessory() == null) {
            throw new IllegalArgumentException("Accessory es requerido");
        }
        
        warranty.setId(generateId());
        csvService.writeLine("warranties.csv", warranty.toCsv());
        return warranty;
    }
    
    /**
     * Obtener todas las garantías
     */
    public List<Warranty> findAll() {
        List<String> csvLines = csvService.readAll("warranties.csv");
        return csvLines.stream()
                .map(line -> {
                    Warranty warranty = new Warranty();
                    warranty.fromCsv(line);
                    
                    String[] parts = line.split(",");
                    Accessory accessory = accessoryService.findById(parts[1]);
                    warranty.setAccessory(accessory);
                    
                    return warranty;
                })
                .collect(Collectors.toList());
    }
}
```

---

## 6. Migración de InventoryService

### Cambios Requeridos

```java
@Service
public class InventoryService {
    @Autowired
    private AccessoryService accessoryService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar inventario por ID y resolver relaciones
     */
    public Inventory findById(String id) {
        String csvLine = csvService.readLine("inventory.csv", id);
        Inventory inventory = new Inventory();
        inventory.fromCsv(csvLine);
        
        // Extraer ID del accesorio
        String[] parts = csvLine.split(",");
        String accessoryId = parts[1];
        
        // Resolver relación con accesorio
        Accessory accessory = accessoryService.findById(accessoryId);
        inventory.setAccessory(accessory);
        
        return inventory;
    }
    
    /**
     * Buscar inventario por accesorio
     */
    public Inventory findByAccessory(Accessory accessory) {
        List<String> csvLines = csvService.readAll("inventory.csv");
        return csvLines.stream()
                .filter(line -> line.split(",")[1].equals(accessory.getId()))
                .map(line -> {
                    Inventory inventory = new Inventory();
                    inventory.fromCsv(line);
                    inventory.setAccessory(accessory);
                    return inventory;
                })
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Crear nuevo registro de inventario
     */
    public Inventory create(Inventory inventory) {
        if (inventory.getAccessory() == null) {
            throw new IllegalArgumentException("Accessory es requerido");
        }
        
        inventory.setId(generateId());
        csvService.writeLine("inventory.csv", inventory.toCsv());
        return inventory;
    }
    
    /**
     * Actualizar inventario
     */
    public Inventory update(Inventory inventory) {
        csvService.updateLine("inventory.csv", inventory.getId(), inventory.toCsv());
        return inventory;
    }
    
    /**
     * Agregar stock
     */
    public void addStock(String inventoryId, int amount) {
        Inventory inventory = findById(inventoryId);
        inventory.addStock(amount);
        update(inventory);
    }
    
    /**
     * Disminuir stock
     */
    public void removeStock(String inventoryId, int amount) {
        Inventory inventory = findById(inventoryId);
        inventory.removeStock(amount);
        update(inventory);
    }
}
```

---

## 7. Migración de InvoiceService

### Cambios Requeridos

```java
@Service
public class InvoiceService {
    @Autowired
    private SaleService saleService;
    
    @Autowired
    private CsvService csvService;
    
    /**
     * Buscar factura por ID y resolver relaciones
     */
    public Invoice findById(String id) {
        String csvLine = csvService.readLine("invoices.csv", id);
        Invoice invoice = new Invoice();
        invoice.fromCsv(csvLine);
        
        // Extraer ID de la venta
        String[] parts = csvLine.split(",");
        String saleId = parts[1];
        
        // Resolver relación con venta
        Sale sale = saleService.findById(saleId);
        invoice.setSale(sale);
        
        return invoice;
    }
    
    /**
     * Crear nueva factura
     */
    public Invoice create(Invoice invoice) {
        if (invoice.getSale() == null) {
            throw new IllegalArgumentException("Sale es requerido");
        }
        
        invoice.setId(generateId());
        invoice.calculateTotal();
        csvService.writeLine("invoices.csv", invoice.toCsv());
        return invoice;
    }
    
    /**
     * Obtener todas las facturas
     */
    public List<Invoice> findAll() {
        List<String> csvLines = csvService.readAll("invoices.csv");
        return csvLines.stream()
                .map(line -> {
                    Invoice invoice = new Invoice();
                    invoice.fromCsv(line);
                    
                    String[] parts = line.split(",");
                    Sale sale = saleService.findById(parts[1]);
                    invoice.setSale(sale);
                    
                    return invoice;
                })
                .collect(Collectors.toList());
    }
}
```

---

## 8. Checklist de Migración

- [ ] Actualizar `OrderService` con resolución de relaciones
- [ ] Actualizar `SaleService` con resolución de relaciones
- [ ] Actualizar `ShoppingCartService` con resolución de relaciones
- [ ] Actualizar `WarrantyService` con resolución de relaciones
- [ ] Actualizar `InventoryService` con resolución de relaciones
- [ ] Actualizar `InvoiceService` con resolución de relaciones
- [ ] Crear `SaleItemService` para gestionar items de venta
- [ ] Actualizar controladores para usar nuevas relaciones
- [ ] Crear tests unitarios para servicios
- [ ] Crear tests de integración
- [ ] Documentar cambios en API
- [ ] Actualizar ejemplos de uso

---

## 9. Consideraciones de Rendimiento

### Problema: N+1 Queries
Cuando se cargan múltiples ventas, cada una carga su cliente y vendedor por separado.

### Solución: Caché
```java
@Service
public class SaleService {
    private Map<String, Client> clientCache = new HashMap<>();
    private Map<String, Seller> sellerCache = new HashMap<>();
    
    private Client getClient(String id) {
        return clientCache.computeIfAbsent(id, 
                k -> clientService.findById(k));
    }
    
    private Seller getSeller(String id) {
        return sellerCache.computeIfAbsent(id, 
                k -> sellerService.findById(k));
    }
}
```

---

## 10. Manejo de Errores

```java
public Order findById(String id) {
    try {
        String csvLine = csvService.readLine("orders.csv", id);
        Order order = new Order();
        order.fromCsv(csvLine);
        
        String[] parts = csvLine.split(",");
        Provider provider = providerService.findById(parts[1]);
        Accessory accessory = accessoryService.findById(parts[2]);
        
        if (provider == null) {
            throw new EntityNotFoundException("Provider not found: " + parts[1]);
        }
        if (accessory == null) {
            throw new EntityNotFoundException("Accessory not found: " + parts[2]);
        }
        
        order.setProvider(provider);
        order.setAccessory(accessory);
        
        return order;
    } catch (IOException e) {
        throw new DataAccessException("Error reading order", e);
    }
}
```
