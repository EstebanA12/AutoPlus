# Ejemplos de Uso del Modelo Refactorizado

## 1. Trabajar con Carrito de Compras

### Crear un carrito y agregar accesorios

```java
// Obtener cliente
Client client = clientService.findById("C001");

// Crear carrito
ShoppingCart cart = new ShoppingCart();
cart.setId("CART001");
cart.setClient(client);
cart.setCreationDate("2024-11-13");

// Obtener accesorios
Accessory interior = accessoryService.findById("ACC001");
Accessory exterior = accessoryService.findById("ACC002");

// Agregar items al carrito
cart.addItem(interior, 2);      // 2 unidades de accesorio interior
cart.addItem(exterior, 1);      // 1 unidad de accesorio exterior

// Obtener información
System.out.println("Items: " + cart.getItemCount());        // 3
System.out.println("Total: $" + cart.getTotalPrice());      // Calcula automáticamente

// Eliminar un item
cart.removeItem(interior);

// Limpiar carrito
cart.clear();
```

---

## 2. Crear una Venta

### Registrar una venta con múltiples artículos

```java
// Obtener cliente y vendedor
Client client = clientService.findById("C001");
Seller seller = sellerService.findById("S001");

// Crear venta
Sale sale = new Sale();
sale.setId("SALE001");
sale.setClient(client);
sale.setSeller(seller);
sale.setSaleDate("2024-11-13");
sale.setStatus("COMPLETED");

// Obtener accesorios
Accessory acc1 = accessoryService.findById("ACC001");
Accessory acc2 = accessoryService.findById("ACC002");

// Crear items de venta
SaleItem item1 = new SaleItem();
item1.setId("ITEM001");
item1.setAccessory(acc1);
item1.setQuantity(2);
item1.setUnitPrice(150000.0);

SaleItem item2 = new SaleItem();
item2.setId("ITEM002");
item2.setAccessory(acc2);
item2.setQuantity(1);
item2.setUnitPrice(200000.0);

// Agregar items a la venta
sale.addItem(item1);
sale.addItem(item2);

// El total se calcula automáticamente
System.out.println("Total venta: $" + sale.getTotalAmount());  // 500000.0

// Acceder a información del cliente y vendedor
System.out.println("Cliente: " + sale.getClient().getName());
System.out.println("Vendedor: " + sale.getSeller().getName());
```

---

## 3. Crear una Factura

### Generar factura a partir de una venta

```java
// Obtener venta
Sale sale = saleService.findById("SALE001");

// Crear factura
Invoice invoice = new Invoice();
invoice.setId("INV001");
invoice.setSale(sale);
invoice.setInvoiceDate("2024-11-13");
invoice.setSubtotal(500000.0);
invoice.setTax(100000.0);  // 20% de impuesto

// Calcular total
invoice.calculateTotal();

System.out.println("Subtotal: $" + invoice.getSubtotal());
System.out.println("Impuesto: $" + invoice.getTax());
System.out.println("Total: $" + invoice.getTotal());

// Acceder a datos de la venta a través de la factura
System.out.println("Cliente: " + invoice.getSale().getClient().getName());
System.out.println("Items vendidos: " + invoice.getSale().getItems().size());
```

---

## 4. Gestionar Inventario

### Controlar stock de accesorios

```java
// Obtener accesorio
Accessory accessory = accessoryService.findById("ACC001");

// Crear registro de inventario
Inventory inventory = new Inventory();
inventory.setId("INV001");
inventory.setAccessory(accessory);
inventory.setQuantity(50);
inventory.setLocation("Almacén A");
inventory.setLastUpdate("2024-11-13");

// Verificar disponibilidad
if (inventory.isInStock()) {
    System.out.println("Producto disponible");
}

// Agregar stock (recibir pedido)
inventory.addStock(20);
System.out.println("Stock actual: " + inventory.getQuantity());  // 70

// Disminuir stock (venta)
try {
    inventory.removeStock(5);
    System.out.println("Stock actual: " + inventory.getQuantity());  // 65
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}

// Intentar vender más de lo disponible
try {
    inventory.removeStock(100);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());  // "Insufficient stock"
}
```

---

## 5. Crear Pedidos a Proveedores

### Realizar pedido de accesorios

```java
// Obtener proveedor y accesorio
Provider provider = providerService.findById("PROV001");
Accessory accessory = accessoryService.findById("ACC001");

// Crear pedido
Order order = new Order();
order.setId("ORD001");
order.setProvider(provider);
order.setAccessory(accessory);
order.setQuantity(100);
order.setTotalCost(15000000.0);
order.setOrderDate("2024-11-13");
order.setStatus("PENDING");

// Acceder a información del proveedor
System.out.println("Proveedor: " + order.getProvider().getName());

// Acceder a información del accesorio
System.out.println("Accesorio: " + order.getAccessory().getName());
System.out.println("Precio unitario: $" + order.getAccessory().getPrice());
```

---

## 6. Gestionar Garantías

### Asignar garantía a un accesorio

```java
// Obtener accesorio
Accessory accessory = accessoryService.findById("ACC001");

// Crear garantía
Warranty warranty = new Warranty();
warranty.setId("WAR001");
warranty.setAccessory(accessory);
warranty.setMonths(24);
warranty.setDescription("Garantía completa contra defectos de fabricación");
warranty.setStartDate("2024-11-13");
warranty.setEndDate("2026-11-13");

// Acceder a información del accesorio
System.out.println("Accesorio garantizado: " + warranty.getAccessory().getName());
System.out.println("Garantía: " + warranty.getMonths() + " meses");
```

---

## 7. Persistencia CSV

### Guardar y cargar datos

```java
// Guardar en CSV
Sale sale = saleService.findById("SALE001");
String csvLine = sale.toCsv();
System.out.println(csvLine);
// Output: SALE001,C001,S001,500000.00,2024-11-13,COMPLETED

// Cargar desde CSV
Sale loadedSale = new Sale();
loadedSale.fromCsv(csvLine);

// Resolver relaciones (en el servicio)
Client client = clientService.findById("C001");
Seller seller = sellerService.findById("S001");
loadedSale.setClient(client);
loadedSale.setSeller(seller);

// Ahora la venta está completa
System.out.println("Cliente: " + loadedSale.getClient().getName());
```

---

## 8. Validaciones Automáticas

### Manejo de relaciones obligatorias

```java
// Intentar crear una venta sin cliente
try {
    Sale sale = new Sale();
    sale.setId("SALE002");
    // sale.setClient(null);  // Esto causaría un error
    sale.setSeller(seller);
    
    // Cuando se intenta usar, lanza NullPointerException
    System.out.println(sale.getClientId());
} catch (NullPointerException e) {
    System.out.println("Error: Cliente es requerido");
}

// Forma correcta
Sale sale = new Sale();
sale.setId("SALE002");
sale.setClient(client);      // Requerido
sale.setSeller(seller);      // Requerido
```

---

## 9. Cálculos Automáticos

### Operaciones que se recalculan automáticamente

```java
// Carrito
ShoppingCart cart = new ShoppingCart();
cart.setClient(client);
cart.addItem(acc1, 2);
cart.addItem(acc2, 3);

// Estos valores se calculan automáticamente
int total = cart.getItemCount();           // 5
double price = cart.getTotalPrice();       // Suma de todos los items

// Venta
Sale sale = new Sale();
sale.setClient(client);
sale.setSeller(seller);
sale.addItem(item1);
sale.addItem(item2);

// El total se recalcula al agregar/eliminar items
double saleTotal = sale.getTotalAmount();  // Suma de todos los items

// Factura
Invoice invoice = new Invoice();
invoice.setSale(sale);
invoice.setSubtotal(500000.0);
invoice.setTax(100000.0);
invoice.calculateTotal();  // 600000.0
```

---

## 10. Flujo Completo de Compra

### Ejemplo de un flujo completo de compra

```java
// 1. Cliente crea carrito
Client client = clientService.findById("C001");
ShoppingCart cart = new ShoppingCart();
cart.setClient(client);
cart.setCreationDate(LocalDate.now().toString());

// 2. Agregar accesorios al carrito
Accessory acc1 = accessoryService.findById("ACC001");
Accessory acc2 = accessoryService.findById("ACC002");
cart.addItem(acc1, 2);
cart.addItem(acc2, 1);

System.out.println("Carrito: " + cart.getItemCount() + " items");
System.out.println("Total carrito: $" + cart.getTotalPrice());

// 3. Procesar venta
Seller seller = sellerService.findById("S001");
Sale sale = new Sale();
sale.setClient(client);
sale.setSeller(seller);
sale.setSaleDate(LocalDate.now().toString());
sale.setStatus("COMPLETED");

// Transferir items del carrito a la venta
for (Map.Entry<Accessory, Integer> entry : cart.getItems().entrySet()) {
    SaleItem item = new SaleItem();
    item.setAccessory(entry.getKey());
    item.setQuantity(entry.getValue());
    item.setUnitPrice(entry.getKey().getPrice());
    sale.addItem(item);
}

System.out.println("Venta: " + sale.getItems().size() + " items");
System.out.println("Total venta: $" + sale.getTotalAmount());

// 4. Generar factura
Invoice invoice = new Invoice();
invoice.setSale(sale);
invoice.setInvoiceDate(LocalDate.now().toString());
invoice.setSubtotal(sale.getTotalAmount());
invoice.setTax(sale.getTotalAmount() * 0.19);  // 19% IVA
invoice.calculateTotal();

System.out.println("Factura generada");
System.out.println("Total a pagar: $" + invoice.getTotal());

// 5. Actualizar inventario
for (SaleItem item : sale.getItems()) {
    Inventory inventory = inventoryService.findByAccessory(item.getAccessory());
    inventory.removeStock(item.getQuantity());
}

// 6. Actualizar cliente
client.setTotalSpent(client.getTotalSpent() + invoice.getTotal());
clientService.update(client);

// 7. Limpiar carrito
cart.clear();

System.out.println("Compra completada exitosamente");
```

---

## Notas Importantes

1. **Relaciones Obligatorias**: Todas las relaciones están marcadas con `@NonNull`, por lo que deben ser asignadas.

2. **Cálculos Automáticos**: Los métodos como `getItemCount()` y `getTotalPrice()` se calculan en tiempo real.

3. **Persistencia**: Los métodos `toCsv()` y `fromCsv()` mantienen compatibilidad con CSV.

4. **Validaciones**: Los métodos de negocio validan sus parámetros (ej: `addStock()` valida cantidad positiva).

5. **Servicios**: La capa de servicios debe resolver las relaciones después de cargar desde CSV.
