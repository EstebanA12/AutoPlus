# AutoPlus - Diagrama de Arquitectura UML

## 📊 Mapa Conceptual - Estructura General

```mermaid
graph TB
    subgraph "Presentación"
        AC["🎮 Controllers"]
        AC1["AccessoryController"]
        AC2["ClientController"]
        AC3["InventoryController"]
        AC4["SaleController"]
        AC5["InvoiceController"]
        AC6["ShoppingCartController"]
        AC7["OrderController"]
        AC8["WarrantyController"]
    end

    subgraph "Lógica de Negocio"
        SVC["🔧 Services"]
        SVC1["AccessoryService"]
        SVC2["ClientService"]
        SVC3["InventoryService"]
        SVC4["SaleService"]
        SVC5["InvoiceService"]
        SVC6["ShoppingCartService"]
        SVC7["OrderService"]
        SVC8["WarrantyService"]
        CSVC["CsvService"]
    end

    subgraph "Modelos de Datos"
        MDL["📦 Model"]
        
        subgraph "Interfaces"
            INT["Sellable<br/>Discountable<br/>Persistable"]
        end
        
        subgraph "Clases Abstractas"
            ABS["Accessory<br/>Employee<br/>Provider"]
        end
        
        subgraph "Entidades"
            ENT["Accessories | Employees | Providers<br/>Client | Inventory | Sale<br/>Invoice | ShoppingCart<br/>Order | Warranty"]
        end
    end

    subgraph "Persistencia"
        CSV["💾 CSV Storage"]
        CSV1["accessories.csv"]
        CSV2["clients.csv"]
        CSV3["inventory.csv"]
        CSV4["sales.csv"]
        CSV5["invoices.csv"]
        CSV6["shopping_carts.csv"]
        CSV7["orders.csv"]
        CSV8["warranties.csv"]
    end

    AC --> AC1 & AC2 & AC3 & AC4 & AC5 & AC6 & AC7 & AC8
    AC1 --> SVC1
    AC2 --> SVC2
    AC3 --> SVC3
    AC4 --> SVC4
    AC5 --> SVC5
    AC6 --> SVC6
    AC7 --> SVC7
    AC8 --> SVC8
    
    SVC1 & SVC2 & SVC3 & SVC4 & SVC5 & SVC6 & SVC7 & SVC8 --> CSVC
    CSVC --> CSV
    
    SVC1 & SVC2 & SVC3 & SVC4 & SVC5 & SVC6 & SVC7 & SVC8 --> MDL
    MDL --> INT & ABS & ENT
    
    CSV --> CSV1 & CSV2 & CSV3 & CSV4 & CSV5 & CSV6 & CSV7 & CSV8
```

---

## 🏗️ Diagrama UML - Jerarquía de Clases

### Accesories (Accesorios)

```mermaid
classDiagram
    class Accessory {
        <<abstract>>
        -id: String
        -name: String
        -description: String
        -price: double
        -stock: int
        -discountPercentage: double
        +getType() String*
        +toCsv() String
        +fromCsv(String)
    }

    class InteriorAccessory {
        -material: String
        -color: String
        +getType() String
    }

    class ExteriorAccessory {
        -material: String
        -resistanceLevel: int
        +getType() String
    }

    class TechnologicalAccessory {
        -technology: String
        -warrantyMonths: int
        +getType() String
    }

    Accessory <|-- InteriorAccessory
    Accessory <|-- ExteriorAccessory
    Accessory <|-- TechnologicalAccessory
```

### Employees (Empleados)

```mermaid
classDiagram
    class Employee {
        <<abstract>>
        -id: String
        -name: String
        -email: String
        -phone: String
        -salary: double
        +calculateBonus() double*
        +toCsv() String
        +fromCsv(String)
    }

    class Seller {
        -commissionPercentage: double
        -salesCount: int
        +calculateBonus() double
    }

    class Manager {
        -teamSize: int
        -bonusPercentage: double
        +calculateBonus() double
    }

    Employee <|-- Seller
    Employee <|-- Manager
```

### Providers (Proveedores)

```mermaid
classDiagram
    class Provider {
        <<abstract>>
        -id: String
        -name: String
        -email: String
        -phone: String
        +calculateDeliveryTime() int*
        +toCsv() String
        +fromCsv(String)
    }

    class LocalProvider {
        -city: String
        -deliveryDays: int
        +calculateDeliveryTime() int
    }

    class InternationalProvider {
        -country: String
        -shippingCost: double
        +calculateDeliveryTime() int
    }

    Provider <|-- LocalProvider
    Provider <|-- InternationalProvider
```

---

## 🔗 Diagrama de Relaciones - Entidades Principales

```mermaid
classDiagram
    class Client {
        -id: String
        -name: String
        -email: String
        -phone: String
        -address: String
        -totalSpent: double
    }

    class Inventory {
        -id: String
        -accessoryId: String
        -quantity: int
        -lastUpdated: LocalDateTime
    }

    class Sale {
        -id: String
        -clientId: String
        -accessoryId: String
        -quantity: int
        -totalPrice: double
        -saleDate: LocalDateTime
    }

    class Invoice {
        -id: String
        -saleId: String
        -clientId: String
        -totalAmount: double
        -issueDate: LocalDateTime
    }

    class ShoppingCart {
        -id: String
        -clientId: String
        -accessories: List~Accessory~
        -totalPrice: double
    }

    class Order {
        -id: String
        -providerId: String
        -accessoryId: String
        -quantity: int
        -orderDate: LocalDateTime
    }

    class Warranty {
        -id: String
        -accessoryId: String
        -months: int
        -coverage: String
        -active: boolean
    }

    Client "1" --> "*" Sale
    Client "1" --> "1" ShoppingCart
    Sale "1" --> "1" Invoice
    ShoppingCart "*" --> "*" Accessory
    Order "*" --> "1" Provider
    Order "*" --> "1" Accessory
    Warranty "1" --> "1" Accessory
    Inventory "1" --> "1" Accessory
```

---

## 📋 Diagrama de Interfaces

```mermaid
classDiagram
    class Sellable {
        <<interface>>
        +getPrice() double*
        +setPrice(double)*
        +getDescription() String*
    }

    class Discountable {
        <<interface>>
        +applyDiscount(double)*
        +getDiscountedPrice() double*
    }

    class Persistable {
        <<interface>>
        +toCsv() String*
        +fromCsv(String)*
    }

    Sellable <|.. Accessory
    Discountable <|.. Accessory
    Persistable <|.. Accessory
    Persistable <|.. Employee
    Persistable <|.. Provider
    Persistable <|.. Client
    Persistable <|.. Inventory
    Persistable <|.. Sale
    Persistable <|.. Invoice
    Persistable <|.. ShoppingCart
    Persistable <|.. Order
    Persistable <|.. Warranty
```

---

## 🔄 Flujo de Datos - Arquitectura por Capas

```mermaid
graph LR
    subgraph "API REST"
        HTTP["HTTP Request"]
    end

    subgraph "Capa de Presentación"
        CTRL["Controller<br/>@RestController"]
    end

    subgraph "Capa de Lógica"
        SVC["Service<br/>@Service"]
        LOGIC["Lógica de Negocio"]
    end

    subgraph "Capa de Datos"
        CSV_SVC["CsvService"]
        MODEL["Model/Entity"]
    end

    subgraph "Persistencia"
        STORAGE["CSV Files<br/>data/csv/"]
    end

    HTTP --> CTRL
    CTRL --> SVC
    SVC --> LOGIC
    LOGIC --> CSV_SVC
    CSV_SVC --> MODEL
    MODEL --> STORAGE
    STORAGE -.->|Read| CSV_SVC
    CSV_SVC -.->|Response| CTRL
    CTRL -.->|JSON| HTTP
```

---

## 📊 Matriz de Responsabilidades

| Capa | Componente | Responsabilidad |
|------|-----------|-----------------|
| **Presentación** | Controllers | Recibir requests HTTP, validar entrada, retornar respuestas |
| **Lógica** | Services | Implementar reglas de negocio, orquestar operaciones |
| **Persistencia** | CsvService | Serializar/deserializar datos a/desde CSV |
| **Modelos** | Entities | Representar datos del dominio, implementar interfaces |
| **Almacenamiento** | CSV Files | Persistencia física de datos |

---

## 🎯 Endpoints Disponibles

```mermaid
graph TD
    API["API REST - Base: /api"]
    
    API --> ACC["🔧 /accessories"]
    API --> CLI["👤 /clients"]
    API --> INV["📦 /inventory"]
    API --> SAL["💰 /sales"]
    API --> INV2["📄 /invoices"]
    API --> CART["🛒 /shopping-carts"]
    API --> ORD["📋 /orders"]
    API --> WAR["🛡️ /warranties"]
    
    ACC --> ACC_GET["GET - Obtener todos"]
    ACC --> ACC_POST["POST - Crear"]
    ACC --> ACC_PUT["PUT - Actualizar"]
    ACC --> ACC_DEL["DELETE - Eliminar"]
    
    CLI --> CLI_GET["GET - Obtener todos"]
    CLI --> CLI_POST["POST - Crear"]
    CLI --> CLI_PUT["PUT - Actualizar"]
    CLI --> CLI_DEL["DELETE - Eliminar"]
```

---

## 🏛️ Principios SOLID Implementados

```mermaid
graph TB
    SOLID["SOLID Principles"]
    
    SOLID --> S["<b>S</b>ingle Responsibility<br/>Cada clase tiene una responsabilidad"]
    SOLID --> O["<b>O</b>pen/Closed<br/>Abierto a extensión, cerrado a modificación"]
    SOLID --> L["<b>L</b>iskov Substitution<br/>Subclases reemplazan a superclases"]
    SOLID --> I["<b>I</b>nterface Segregation<br/>Interfaces específicas y pequeñas"]
    SOLID --> D["<b>D</b>ependency Inversion<br/>Depender de abstracciones"]
    
    S --> EX1["Services, Controllers, Entities"]
    O --> EX2["Clases abstractas extensibles"]
    L --> EX3["Accessory, Employee, Provider"]
    I --> EX4["Sellable, Discountable, Persistable"]
    D --> EX5["Inyección de dependencias"]
```

---

## 📁 Estructura de Directorios

```
AutoPlus/
├── src/main/java/co/edu/umanizales/autoplus/
│   ├── controller/
│   │   ├── AccessoryController.java
│   │   ├── ClientController.java
│   │   ├── InventoryController.java
│   │   ├── SaleController.java
│   │   ├── InvoiceController.java
│   │   ├── ShoppingCartController.java
│   │   ├── OrderController.java
│   │   ├── WarrantyController.java
│   │   └── Prog3Controller.java
│   ├── service/
│   │   ├── AccessoryService.java
│   │   ├── ClientService.java
│   │   ├── InventoryService.java
│   │   ├── SaleService.java
│   │   ├── InvoiceService.java
│   │   ├── ShoppingCartService.java
│   │   ├── OrderService.java
│   │   ├── WarrantyService.java
│   │   └── CsvService.java
│   ├── model/
│   │   ├── abstracts/
│   │   │   ├── Accessory.java
│   │   │   ├── Employee.java
│   │   │   └── Provider.java
│   │   ├── interfaces/
│   │   │   ├── Sellable.java
│   │   │   ├── Discountable.java
│   │   │   └── Persistable.java
│   │   └── entities/
│   │       ├── InteriorAccessory.java
│   │       ├── ExteriorAccessory.java
│   │       ├── TechnologicalAccessory.java
│   │       ├── Seller.java
│   │       ├── Manager.java
│   │       ├── LocalProvider.java
│   │       ├── InternationalProvider.java
│   │       ├── Client.java
│   │       ├── Inventory.java
│   │       ├── Sale.java
│   │       ├── Invoice.java
│   │       ├── ShoppingCart.java
│   │       ├── Order.java
│   │       └── Warranty.java
│   └── AutoPlusApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── proyecto.md
│   └── ARCHITECTURE_DIAGRAM.md (Este archivo)
├── data/csv/
│   ├── accessories.csv
│   ├── clients.csv
│   ├── inventory.csv
│   ├── sales.csv
│   ├── invoices.csv
│   ├── shopping_carts.csv
│   ├── orders.csv
│   └── warranties.csv
├── pom.xml
└── README.md
```

---

## 🔑 Conceptos Clave

### Herencia
- **Accessory** → InteriorAccessory, ExteriorAccessory, TechnologicalAccessory
- **Employee** → Seller, Manager
- **Provider** → LocalProvider, InternationalProvider

### Polimorfismo
- Método `getType()` implementado diferente en cada tipo de accesorio
- Método `calculateBonus()` implementado diferente en Seller y Manager
- Método `calculateDeliveryTime()` implementado diferente en proveedores

### Encapsulamiento
- Atributos privados con getters/setters automáticos (Lombok @Data)
- Acceso controlado a datos sensibles

### Interfaces
- **Sellable**: Define comportamiento de venta (precio, descripción)
- **Discountable**: Define comportamiento de descuentos
- **Persistable**: Define serialización a CSV

### Composición
- Invoice contiene datos de Sale
- ShoppingCart contiene múltiples Accessories
- Order relaciona Provider con Accessory

---

## 🚀 Tecnologías

- **Java 23+** - Lenguaje de programación
- **Spring Boot 3.x** - Framework web
- **Lombok** - Reducción de boilerplate
- **Maven** - Gestor de dependencias
- **CSV** - Persistencia de datos

---

**Última actualización:** 2025-11-04
**Autor:** Carlos Alberto Loaiza Guerrero
