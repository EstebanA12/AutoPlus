# AutoPlus - Modelo Refactorizado con Relaciones de Objetos

## Resumen de Cambios

Se ha refactorizado el modelo de datos para implementar **relaciones de objetos reales** en lugar de solo IDs string. Esto mejora:

- ✅ **Integridad referencial**: Las entidades relacionadas se validan automáticamente
- ✅ **Navegabilidad**: Acceso directo a objetos relacionados sin búsquedas adicionales
- ✅ **Encapsulamiento**: Métodos de negocio en las entidades
- ✅ **Mantenibilidad**: Código más legible y fácil de mantener
- ✅ **Persistencia**: Compatibilidad con CSV mediante métodos de conveniencia

## Diagrama de Relaciones

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENTE (Client)                        │
│  - id, name, email, phone, address, totalSpent              │
└────────────────┬──────────────────────────────────────────┬─┘
                 │                                          │
                 │ 1:N                                      │ 1:N
                 ▼                                          ▼
        ┌──────────────────┐                    ┌──────────────────┐
        │  CARRITO (Cart)  │                    │  VENTA (Sale)    │
        │  - items: Map    │                    │  - items: List   │
        │  - client        │                    │  - client        │
        │  - creationDate  │                    │  - seller        │
        └────────┬─────────┘                    └────────┬─────────┘
                 │                                       │
                 │ N:N                                   │ 1:N
                 ▼                                       ▼
        ┌──────────────────────┐           ┌──────────────────────┐
        │ ACCESORIO (Accessory)│           │  ITEM VENTA (SaleItem)
        │ - id, name, price    │           │  - accessory         │
        │ - stock, description │           │  - quantity          │
        └──────────┬───────────┘           │  - unitPrice         │
                   │                       └──────────┬───────────┘
                   │ 1:N                             │
                   ├──────────────────────────────────┘
                   │
                   │ 1:N
                   ▼
        ┌──────────────────────┐
        │ INVENTARIO (Inventory)
        │ - accessory          │
        │ - quantity           │
        │ - location           │
        │ - lastUpdate         │
        └──────────┬───────────┘
                   │
                   │ 1:N
                   ▼
        ┌──────────────────────┐
        │ GARANTÍA (Warranty)  │
        │ - accessory          │
        │ - months             │
        │ - description        │
        └──────────────────────┘

        ┌──────────────────────┐
        │ PEDIDO (Order)       │
        │ - provider           │
        │ - accessory          │
        │ - quantity           │
        │ - totalCost          │
        └──────────────────────┘

        ┌──────────────────────┐
        │ FACTURA (Invoice)    │
        │ - sale               │
        │ - subtotal           │
        │ - tax                │
        │ - total              │
        └──────────────────────┘
```

## Entidades Refactorizadas

### 1. **Order** (Pedido)
```java
private Provider provider;      // Relación con proveedor
private Accessory accessory;    // Relación con accesorio
private int quantity;
private double totalCost;

// Métodos de conveniencia
public String getProviderId()   // Para persistencia CSV
public String getAccessoryId()  // Para persistencia CSV
```

**Mejoras:**
- Acceso directo a datos del proveedor y accesorio
- Validación automática con `@NonNull`
- Métodos para obtener IDs cuando sea necesario

---

### 2. **ShoppingCart** (Carrito de Compras)
```java
private Client client;                          // Relación con cliente
private Map<Accessory, Integer> items;          // Colección de accesorios

// Métodos de negocio
public void addItem(Accessory accessory, int quantity)
public void removeItem(Accessory accessory)
public int getItemCount()                       // Calcula automáticamente
public double getTotalPrice()                   // Calcula automáticamente
public void clear()
```

**Mejoras:**
- Gestión de múltiples accesorios en una estructura Map
- Cálculos automáticos de cantidad y precio total
- Métodos para manipular el carrito

---

### 3. **Sale** (Venta)
```java
private Client client;                  // Relación con cliente
private Seller seller;                  // Relación con vendedor
private List<SaleItem> items;           // Colección de artículos vendidos

// Métodos de negocio
public void addItem(SaleItem item)
public void removeItem(SaleItem item)
private void recalculateTotal()         // Recalcula automáticamente
```

**Mejoras:**
- Relaciones explícitas con cliente y vendedor
- Gestión de múltiples artículos por venta
- Recálculo automático de totales

---

### 4. **SaleItem** (Artículo de Venta) - NUEVA
```java
private Accessory accessory;            // Relación con accesorio
private int quantity;
private double unitPrice;

// Métodos de negocio
public double getSubtotal()             // Calcula cantidad × precio
```

**Propósito:**
- Representa un accesorio específico dentro de una venta
- Permite múltiples accesorios por venta con cantidades diferentes
- Mantiene el precio unitario al momento de la venta

---

### 5. **Invoice** (Factura)
```java
private Sale sale;                      // Relación con venta
private double subtotal;
private double tax;
private double total;

// Métodos de negocio
public void calculateTotal()            // Calcula subtotal + tax
```

**Mejoras:**
- Relación directa con la venta
- Acceso a todos los datos de la venta a través del objeto sale
- Método para recalcular el total

---

### 6. **Warranty** (Garantía)
```java
private Accessory accessory;            // Relación con accesorio
private int months;
private String description;
```

**Mejoras:**
- Relación directa con el accesorio garantizado
- Validación automática con `@NonNull`

---

### 7. **Inventory** (Inventario)
```java
private Accessory accessory;            // Relación con accesorio
private int quantity;
private String location;

// Métodos de negocio
public void addStock(int amount)        // Aumenta stock
public void removeStock(int amount)     // Disminuye stock con validación
public boolean isInStock()              // Verifica disponibilidad
```

**Mejoras:**
- Relación directa con el accesorio
- Métodos para gestionar stock con validaciones
- Verificación de disponibilidad

---

## Patrones Implementados

### 1. **Composición**
- `ShoppingCart` contiene `Map<Accessory, Integer>`
- `Sale` contiene `List<SaleItem>`
- `Invoice` contiene `Sale`

### 2. **Agregación**
- `Order` agrega `Provider` y `Accessory`
- `Sale` agrega `Client` y `Seller`
- `Warranty` agrega `Accessory`

### 3. **Validación con @NonNull**
- Garantiza que las relaciones siempre existan
- Lanza `NullPointerException` si se intenta asignar null

### 4. **Métodos de Conveniencia**
- `getProviderId()`, `getClientId()`, etc.
- Permiten acceso a IDs para persistencia CSV
- Mantienen compatibilidad con la capa de servicios

### 5. **Métodos de Negocio**
- `addItem()`, `removeItem()` en carrito y venta
- `addStock()`, `removeStock()` en inventario
- `calculateTotal()` en factura
- Encapsulan lógica de negocio en las entidades

---

## Persistencia CSV

Aunque las entidades ahora usan relaciones de objetos, la persistencia CSV se mantiene:

```java
@Override
public String toCsv() {
    // Usa métodos de conveniencia para obtener IDs
    return String.format("%s,%s,%s,...",
            id, getProviderId(), getAccessoryId(), ...);
}

@Override
public void fromCsv(String csvLine) {
    // Parsea los datos pero los objetos se asignan en la capa de servicios
    String[] parts = csvLine.split(",");
    this.id = parts[0];
    // provider/accessory se asignan después mediante setters
}
```

---

## Cambios en la Capa de Servicios

Los servicios ahora deben:

1. **Cargar datos del CSV** con `fromCsv()`
2. **Resolver relaciones** buscando objetos relacionados
3. **Asignar objetos** a través de setters
4. **Guardar en CSV** con `toCsv()`

Ejemplo:
```java
// Cargar orden del CSV
Order order = new Order();
order.fromCsv(csvLine);

// Resolver relaciones
Provider provider = providerService.findById(providerId);
Accessory accessory = accessoryService.findById(accessoryId);

// Asignar objetos
order.setProvider(provider);
order.setAccessory(accessory);
```

---

## Ventajas de la Refactorización

| Aspecto | Antes | Después |
|--------|-------|---------|
| **Relaciones** | Solo IDs string | Objetos completos |
| **Navegabilidad** | Búsquedas manuales | Acceso directo |
| **Validación** | Manual | Automática con @NonNull |
| **Integridad** | No garantizada | Garantizada |
| **Métodos** | Lógica en servicios | Encapsulados en entidades |
| **Mantenibilidad** | Difícil | Fácil |
| **Escalabilidad** | Limitada | Mejorada |

---

## Próximos Pasos

1. **Actualizar servicios** para resolver relaciones
2. **Agregar validaciones** en constructores
3. **Implementar equals() y hashCode()** para comparaciones
4. **Agregar métodos de búsqueda** en servicios
5. **Crear tests unitarios** para relaciones
