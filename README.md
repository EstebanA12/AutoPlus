# AutoPlus - API REST de Gestión de Tienda de Accesorios Automotrices

## Descripción del Proyecto

AutoPlus es una API REST desarrollada en **Java 23+** con **Spring Boot** y **Lombok** para la gestión completa de una tienda de accesorios automotrices. El sistema implementa principios sólidos de Programación Orientada a Objetos (POO) y persiste datos en archivos **CSV**.

## Características Principales

✅ **10+ Clases** implementadas con POO completa
✅ **Herencia** - Jerarquías de clases (Accessory, Employee, Provider)
✅ **Polimorfismo** - Métodos sobrescritos en subclases
✅ **Interfaces** - Sellable, Discountable, Persistable
✅ **Encapsulamiento** - Getters/Setters con Lombok
✅ **Persistencia CSV** - Almacenamiento en archivos locales
✅ **API REST CRUD** - Operaciones completas para todas las entidades

## Estructura del Proyecto

```
src/main/java/co/edu/umanizales/autoplus/
├── model/
│   ├── interfaces/
│   │   ├── Sellable.java
│   │   ├── Discountable.java
│   │   └── Persistable.java
│   ├── abstracts/
│   │   ├── Accessory.java
│   │   ├── Employee.java
│   │   └── Provider.java
│   └── entities/
│       ├── InteriorAccessory.java
│       ├── ExteriorAccessory.java
│       ├── TechnologicalAccessory.java
│       ├── Client.java
│       ├── Seller.java
│       ├── Manager.java
│       ├── LocalProvider.java
│       ├── InternationalProvider.java
│       ├── Inventory.java
│       ├── Sale.java
│       ├── Invoice.java
│       ├── ShoppingCart.java
│       ├── Order.java
│       └── Warranty.java
├── service/
│   ├── CsvService.java
│   ├── AccessoryService.java
│   ├── ClientService.java
│   ├── InventoryService.java
│   ├── SaleService.java
│   ├── InvoiceService.java
│   ├── ShoppingCartService.java
│   ├── OrderService.java
│   └── WarrantyService.java
└── controller/
    ├── AccessoryController.java
    ├── ClientController.java
    ├── InventoryController.java
    ├── SaleController.java
    ├── InvoiceController.java
    ├── ShoppingCartController.java
    ├── OrderController.java
    └── WarrantyController.java
```

## Entidades Principales

### 1. **Accessory** (Clase Abstracta)
- **Subclases:**
  - `InteriorAccessory` - Accesorios de interior (material, color)
  - `ExteriorAccessory` - Accesorios de exterior (material, resistencia)
  - `TechnologicalAccessory` - Accesorios tecnológicos (tecnología, garantía)

### 2. **Employee** (Clase Abstracta)
- **Subclases:**
  - `Seller` - Vendedor (comisión, cantidad de ventas)
  - `Manager` - Gerente (tamaño del equipo, bonificación)

### 3. **Provider** (Clase Abstracta)
- **Subclases:**
  - `LocalProvider` - Proveedor local (ciudad, días de entrega)
  - `InternationalProvider` - Proveedor internacional (país, costo de envío)

### 4. **Otras Entidades**
- `Client` - Cliente de la tienda
- `Inventory` - Gestión de inventario
- `Sale` - Registro de ventas
- `Invoice` - Facturas
- `ShoppingCart` - Carrito de compras
- `Order` - Pedidos a proveedores
- `Warranty` - Garantías de productos

## Interfaces Implementadas

### **Sellable**
```java
- getPrice()
- setPrice(double price)
- getDescription()
```

### **Discountable**
```java
- applyDiscount(double discountPercentage)
- getDiscountedPrice()
```

### **Persistable**
```java
- toCsv()
- fromCsv(String csvLine)
```

## Endpoints de la API

### Accesorios
- `GET /api/accessories` - Obtener todos
- `GET /api/accessories/{id}` - Obtener por ID
- `POST /api/accessories` - Crear
- `PUT /api/accessories/{id}` - Actualizar
- `DELETE /api/accessories/{id}` - Eliminar

### Clientes
- `GET /api/clients` - Obtener todos
- `GET /api/clients/{id}` - Obtener por ID
- `POST /api/clients` - Crear
- `PUT /api/clients/{id}` - Actualizar
- `DELETE /api/clients/{id}` - Eliminar

### Inventario
- `GET /api/inventory` - Obtener todos
- `GET /api/inventory/{id}` - Obtener por ID
- `POST /api/inventory` - Crear
- `PUT /api/inventory/{id}` - Actualizar
- `DELETE /api/inventory/{id}` - Eliminar

### Ventas
- `GET /api/sales` - Obtener todas
- `GET /api/sales/{id}` - Obtener por ID
- `POST /api/sales` - Crear
- `PUT /api/sales/{id}` - Actualizar
- `DELETE /api/sales/{id}` - Eliminar

### Facturas
- `GET /api/invoices` - Obtener todas
- `GET /api/invoices/{id}` - Obtener por ID
- `POST /api/invoices` - Crear
- `PUT /api/invoices/{id}` - Actualizar
- `DELETE /api/invoices/{id}` - Eliminar

### Carrito de Compras
- `GET /api/shopping-carts` - Obtener todos
- `GET /api/shopping-carts/{id}` - Obtener por ID
- `POST /api/shopping-carts` - Crear
- `PUT /api/shopping-carts/{id}` - Actualizar
- `DELETE /api/shopping-carts/{id}` - Eliminar

### Pedidos
- `GET /api/orders` - Obtener todos
- `GET /api/orders/{id}` - Obtener por ID
- `POST /api/orders` - Crear
- `PUT /api/orders/{id}` - Actualizar
- `DELETE /api/orders/{id}` - Eliminar

### Garantías
- `GET /api/warranties` - Obtener todas
- `GET /api/warranties/{id}` - Obtener por ID
- `POST /api/warranties` - Crear
- `PUT /api/warranties/{id}` - Actualizar
- `DELETE /api/warranties/{id}` - Eliminar

## Persistencia CSV

Los datos se almacenan en archivos CSV en la carpeta `data/csv/`:
- `accessories.csv` - Accesorios
- `clients.csv` - Clientes
- `inventory.csv` - Inventario
- `sales.csv` - Ventas
- `invoices.csv` - Facturas
- `shopping_carts.csv` - Carritos
- `orders.csv` - Pedidos
- `warranties.csv` - Garantías

## Tecnologías Utilizadas

- **Java 23+**
- **Spring Boot 3.x**
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias
- **CSV** - Persistencia de datos

## Principios POO Implementados

### 1. **Encapsulamiento**
- Uso de `@Data` de Lombok para getters/setters automáticos
- Atributos privados con acceso controlado

### 2. **Herencia**
- `Accessory` → `InteriorAccessory`, `ExteriorAccessory`, `TechnologicalAccessory`
- `Employee` → `Seller`, `Manager`
- `Provider` → `LocalProvider`, `InternationalProvider`

### 3. **Polimorfismo**
- Métodos abstractos implementados en subclases
- `getType()` en cada tipo de accesorio
- `calculateBonus()` en empleados
- `calculateDeliveryTime()` en proveedores

### 4. **Interfaces**
- `Sellable` - Define comportamiento de venta
- `Discountable` - Define comportamiento de descuentos
- `Persistable` - Define comportamiento de persistencia

### 5. **Composición y Agregación**
- `Invoice` contiene datos de `Sale`
- `ShoppingCart` contiene múltiples accesorios
- `Order` relaciona `Provider` con `Accessory`

## Cómo Ejecutar

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd AutoPlus
```

2. **Compilar el proyecto**
```bash
mvn clean install
```

3. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

4. **Acceder a la API**
```
http://localhost:8080/api/...
```

## Ejemplo de Uso

### Crear un Cliente
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "id": "C001",
    "name": "Juan Pérez",
    "email": "juan@example.com",
    "phone": "3001234567",
    "address": "Calle 10 #20-30",
    "totalSpent": 0.0
  }'
```

### Crear un Accesorio Interior
```bash
curl -X POST http://localhost:8080/api/accessories \
  -H "Content-Type: application/json" \
  -d '{
    "id": "ACC001",
    "name": "Tapetes de Auto",
    "description": "Tapetes de lujo para interior",
    "price": 150000.0,
    "stock": 50,
    "discountPercentage": 0.0,
    "material": "Cuero",
    "color": "Negro"
  }'
```

## Notas Importantes

- Los archivos CSV se crean automáticamente en `data/csv/`
- Cada entidad implementa la interfaz `Persistable` para serialización CSV
- Los servicios cargan datos en memoria al iniciar operaciones
- La API es stateless y persiste cambios en CSV

## Autor

Desarrollado por: **Carlos Alberto Loaiza Guerrero**

## Licencia

Este proyecto es de uso educativo.
