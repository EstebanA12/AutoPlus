# Diagramas UML del Modelo Refactorizado

## 1. Diagrama de Clases - Relaciones Principales

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                          DIAGRAMA DE RELACIONES                            │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

                              ┌──────────────┐
                              │   Client     │
                              ├──────────────┤
                              │ - id: String │
                              │ - name       │
                              │ - email      │
                              │ - phone      │
                              │ - address    │
                              └──────────────┘
                                    ▲
                    ┌───────────────┼───────────────┐
                    │               │               │
              1:N   │         1:N    │         1:N   │
                    │               │               │
            ┌───────┴────┐  ┌───────┴────┐  ┌──────┴─────┐
            │            │  │            │  │            │
      ┌─────┴──────┐ ┌──┴──┴───┐ ┌─────┴──┴──┐
      │ShoppingCart│ │  Sale   │ │  Invoice  │
      ├────────────┤ ├─────────┤ ├───────────┤
      │- id        │ │- id     │ │- id       │
      │- client    │ │- client │ │- sale     │
      │- items: Map│ │- seller │ │- subtotal │
      │- createdAt │ │- items  │ │- tax      │
      └────────────┘ │- total  │ │- total    │
            ▲        │- date   │ └───────────┘
            │        │- status │
            │        └─────────┘
            │              ▲
            │              │
            │         1:N  │
            │              │
            │        ┌─────┴──────┐
            │        │  SaleItem  │
            │        ├────────────┤
            │        │- id        │
            │        │- accessory │
            │        │- quantity  │
            │        │- unitPrice │
            │        └────────────┘
            │              ▲
            │              │
            │         N:N  │
            │              │
      ┌─────┴──────────────┴────────────┐
      │                                  │
      │         Accessory (Abstract)     │
      │         ├────────────────────┤   │
      │         │- id: String        │   │
      │         │- name              │   │
      │         │- price             │   │
      │         │- stock             │   │
      │         └────────────────────┘   │
      │                                  │
      │         Subclases:               │
      │         • InteriorAccessory      │
      │         • ExteriorAccessory      │
      │         • TechnologicalAccessory │
      │                                  │
      └──────────────────────────────────┘
            ▲              ▲
            │              │
       1:N  │         1:N  │
            │              │
      ┌─────┴────┐   ┌─────┴────────┐
      │Inventory │   │  Warranty    │
      ├──────────┤   ├──────────────┤
      │- id      │   │- id          │
      │- accessory   │- accessory   │
      │- quantity│   │- months      │
      │- location│   │- description │
      └──────────┘   └──────────────┘


                    ┌──────────────────┐
                    │  Provider (Abs)  │
                    ├──────────────────┤
                    │- id: String      │
                    │- name            │
                    │- email           │
                    │- phone           │
                    └──────────────────┘
                           ▲
                           │
                      1:N  │
                           │
                    ┌──────┴──────┐
                    │   Order     │
                    ├─────────────┤
                    │- id         │
                    │- provider   │
                    │- accessory  │
                    │- quantity   │
                    │- totalCost  │
                    │- date       │
                    │- status     │
                    └─────────────┘
                           ▲
                           │
                      N:1  │
                           │
                    Accessory
```

---

## 2. Diagrama de Composición - ShoppingCart

```
┌─────────────────────────────────────────────────────┐
│                  ShoppingCart                       │
│  ┌───────────────────────────────────────────────┐  │
│  │ - id: String                                  │  │
│  │ - client: Client ─────────────────────────┐   │  │
│  │ - items: Map<Accessory, Integer>          │   │  │
│  │ - creationDate: String                    │   │  │
│  │                                           │   │  │
│  │ + addItem(Accessory, int)                 │   │  │
│  │ + removeItem(Accessory)                   │   │  │
│  │ + getItemCount(): int                     │   │  │
│  │ + getTotalPrice(): double                 │   │  │
│  │ + clear()                                 │   │  │
│  └───────────────────────────────────────────┘   │  │
│                                                   │  │
│  Composición de Items:                           │  │
│  ┌────────────────────────────────────────────┐  │  │
│  │ Map<Accessory, Integer>                    │  │  │
│  │                                            │  │  │
│  │ Accesorio1 → 2 unidades                    │  │  │
│  │ Accesorio2 → 3 unidades                    │  │  │
│  │ Accesorio3 → 1 unidad                      │  │  │
│  │                                            │  │  │
│  │ Total: 6 items                             │  │  │
│  │ Precio Total: $XXX.XX                      │  │  │
│  └────────────────────────────────────────────┘   │  │
│                                                   │  │
└─────────────────────────────────────────────────────┘
                           │
                           │ Referencia a
                           ▼
                    ┌──────────────┐
                    │    Client    │
                    ├──────────────┤
                    │ - id         │
                    │ - name       │
                    │ - email      │
                    │ - phone      │
                    │ - address    │
                    └──────────────┘
```

---

## 3. Diagrama de Composición - Sale

```
┌──────────────────────────────────────────────────────┐
│                      Sale                            │
│  ┌────────────────────────────────────────────────┐  │
│  │ - id: String                                   │  │
│  │ - client: Client ────────────────────────┐     │  │
│  │ - seller: Seller ────────────────────┐   │     │  │
│  │ - items: List<SaleItem>              │   │     │  │
│  │ - totalAmount: double                │   │     │  │
│  │ - saleDate: String                   │   │     │  │
│  │ - status: String                     │   │     │  │
│  │                                      │   │     │  │
│  │ + addItem(SaleItem)                  │   │     │  │
│  │ + removeItem(SaleItem)               │   │     │  │
│  │ + recalculateTotal()                 │   │     │  │
│  └────────────────────────────────────────┼─┼─────┘  │
│                                           │ │        │
│  Lista de Items:                          │ │        │
│  ┌──────────────────────────────────────┐ │ │        │
│  │ SaleItem 1                           │ │ │        │
│  │ ├─ accessory: Accesorio1             │ │ │        │
│  │ ├─ quantity: 2                       │ │ │        │
│  │ ├─ unitPrice: 150000.0               │ │ │        │
│  │ └─ subtotal: 300000.0                │ │ │        │
│  │                                      │ │ │        │
│  │ SaleItem 2                           │ │ │        │
│  │ ├─ accessory: Accesorio2             │ │ │        │
│  │ ├─ quantity: 1                       │ │ │        │
│  │ ├─ unitPrice: 200000.0               │ │ │        │
│  │ └─ subtotal: 200000.0                │ │ │        │
│  │                                      │ │ │        │
│  │ Total Sale: 500000.0                 │ │ │        │
│  └──────────────────────────────────────┘ │ │        │
│                                           │ │        │
└───────────────────────────────────────────┼─┼────────┘
                                            │ │
                                    Ref a   │ │   Ref a
                                            ▼ ▼
                                    ┌──────────────┐
                                    │    Client    │
                                    └──────────────┘
                                    
                                    ┌──────────────┐
                                    │    Seller    │
                                    └──────────────┘
```

---

## 4. Diagrama de Agregación - Order

```
┌─────────────────────────────────────────────────┐
│                    Order                        │
│  ┌───────────────────────────────────────────┐  │
│  │ - id: String                              │  │
│  │ - provider: Provider ──────────────────┐  │  │
│  │ - accessory: Accessory ────────────┐   │  │  │
│  │ - quantity: int                    │   │  │  │
│  │ - totalCost: double                │   │  │  │
│  │ - orderDate: String                │   │  │  │
│  │ - status: String                   │   │  │  │
│  │                                    │   │  │  │
│  │ + getProviderId(): String          │   │  │  │
│  │ + getAccessoryId(): String         │   │  │  │
│  └────────────────────────────────────┼───┼──┘  │
│                                       │   │     │
└───────────────────────────────────────┼───┼─────┘
                                        │   │
                            Agregación  │   │
                                        ▼   ▼
                            ┌──────────────────────┐
                            │  Provider (Abstract) │
                            ├──────────────────────┤
                            │ - id                 │
                            │ - name               │
                            │ - email              │
                            │ - phone              │
                            └──────────────────────┘
                            
                            ┌──────────────────────┐
                            │  Accessory (Abstract)│
                            ├──────────────────────┤
                            │ - id                 │
                            │ - name               │
                            │ - price              │
                            │ - stock              │
                            └──────────────────────┘
```

---

## 5. Diagrama de Relaciones - Inventory

```
┌─────────────────────────────────────────────────┐
│                  Inventory                      │
│  ┌───────────────────────────────────────────┐  │
│  │ - id: String                              │  │
│  │ - accessory: Accessory ──────────────┐    │  │
│  │ - quantity: int                      │    │  │
│  │ - location: String                   │    │  │
│  │ - lastUpdate: String                 │    │  │
│  │                                      │    │  │
│  │ + addStock(int)                      │    │  │
│  │ + removeStock(int)                   │    │  │
│  │ + isInStock(): boolean               │    │  │
│  │ + getAccessoryId(): String           │    │  │
│  └──────────────────────────────────────┼────┘  │
│                                         │       │
└─────────────────────────────────────────┼───────┘
                                          │
                                    1:1   │
                                          ▼
                            ┌──────────────────────┐
                            │  Accessory (Abstract)│
                            ├──────────────────────┤
                            │ - id: String         │
                            │ - name: String       │
                            │ - price: double      │
                            │ - stock: int         │
                            │ - description: String│
                            └──────────────────────┘
                                      ▲
                                      │
                                 1:N  │
                                      │
                    ┌─────────────────┼──────────────┐
                    │                 │              │
            ┌───────┴────────┐ ┌──────┴──────┐ ┌────┴──────────┐
            │ InteriorAcc    │ │ ExteriorAcc │ │ TechnologicalAcc
            ├────────────────┤ ├─────────────┤ ├────────────────┤
            │ - material     │ │ - material  │ │ - technology   │
            │ - color        │ │ - resistance│ │ - warranty     │
            └────────────────┘ └─────────────┘ └────────────────┘
```

---

## 6. Diagrama de Relaciones - Warranty

```
┌─────────────────────────────────────────────────┐
│                  Warranty                       │
│  ┌───────────────────────────────────────────┐  │
│  │ - id: String                              │  │
│  │ - accessory: Accessory ──────────────┐    │  │
│  │ - months: int                        │    │  │
│  │ - description: String                │    │  │
│  │ - startDate: String                  │    │  │
│  │ - endDate: String                    │    │  │
│  │                                      │    │  │
│  │ + getAccessoryId(): String           │    │  │
│  └──────────────────────────────────────┼────┘  │
│                                         │       │
└─────────────────────────────────────────┼───────┘
                                          │
                                    1:1   │
                                          ▼
                            ┌──────────────────────┐
                            │  Accessory (Abstract)│
                            ├──────────────────────┤
                            │ - id: String         │
                            │ - name: String       │
                            │ - price: double      │
                            │ - stock: int         │
                            └──────────────────────┘
```

---

## 7. Diagrama de Relaciones - Invoice

```
┌──────────────────────────────────────────────────┐
│                   Invoice                        │
│  ┌────────────────────────────────────────────┐  │
│  │ - id: String                               │  │
│  │ - sale: Sale ──────────────────────────┐   │  │
│  │ - subtotal: double                     │   │  │
│  │ - tax: double                          │   │  │
│  │ - total: double                        │   │  │
│  │ - invoiceDate: String                  │   │  │
│  │                                        │   │  │
│  │ + calculateTotal()                     │   │  │
│  │ + getSaleId(): String                  │   │  │
│  └────────────────────────────────────────┼───┘  │
│                                           │      │
└───────────────────────────────────────────┼──────┘
                                            │
                                      1:1   │
                                            ▼
                            ┌──────────────────────┐
                            │       Sale           │
                            ├──────────────────────┤
                            │ - id: String         │
                            │ - client: Client     │
                            │ - seller: Seller     │
                            │ - items: List        │
                            │ - totalAmount: double│
                            │ - saleDate: String   │
                            │ - status: String     │
                            └──────────────────────┘
```

---

## 8. Diagrama de Secuencia - Flujo de Compra

```
Cliente    ShoppingCart    Sale    SaleItem    Inventory    Invoice
   │            │           │         │           │            │
   │ addItem    │           │         │           │            │
   ├───────────>│           │         │           │            │
   │            │           │         │           │            │
   │ checkout   │           │         │           │            │
   ├───────────────────────>│         │           │            │
   │            │           │ create  │           │            │
   │            │           ├────────>│           │            │
   │            │           │         │           │            │
   │            │           │ addItem │           │            │
   │            │           ├────────>│           │            │
   │            │           │         │           │            │
   │            │           │         │ removeStock           │
   │            │           │         ├──────────>│            │
   │            │           │         │           │            │
   │            │           │ create  │           │            │
   │            │           ├────────────────────────────────>│
   │            │           │         │           │            │
   │ receipt    │           │         │           │ return     │
   │<─────────────────────────────────────────────────────────┤
   │            │           │         │           │            │
```

---

## 9. Diagrama de Dependencias

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPAS DEL SISTEMA                        │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE PRESENTACIÓN                      │
│  (Controllers: OrderController, SaleController, etc.)       │
└────────────────────────┬────────────────────────────────────┘
                         │ Depende de
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE SERVICIOS                        │
│  (Services: OrderService, SaleService, etc.)                │
└────────────────────────┬────────────────────────────────────┘
                         │ Depende de
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE MODELOS                          │
│  (Entities: Order, Sale, SaleItem, Inventory, etc.)         │
│  (Interfaces: Persistable, Sellable, Discountable)          │
│  (Abstracts: Accessory, Employee, Provider)                 │
└────────────────────────┬────────────────────────────────────┘
                         │ Depende de
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE PERSISTENCIA                      │
│  (CsvService: Lectura/Escritura de archivos CSV)            │
└─────────────────────────────────────────────────────────────┘
```

---

## 10. Matriz de Relaciones

| Entidad | Relación | Tipo | Cardinalidad |
|---------|----------|------|--------------|
| Order | Provider | Agregación | 1:N |
| Order | Accessory | Agregación | 1:N |
| Sale | Client | Agregación | 1:N |
| Sale | Seller | Agregación | 1:N |
| Sale | SaleItem | Composición | 1:N |
| SaleItem | Accessory | Agregación | N:1 |
| ShoppingCart | Client | Agregación | 1:1 |
| ShoppingCart | Accessory | Composición | N:N |
| Invoice | Sale | Agregación | 1:1 |
| Warranty | Accessory | Agregación | 1:N |
| Inventory | Accessory | Agregación | 1:1 |

---

## Leyenda

```
┌──────────────┐
│   Entidad    │  Clase concreta
└──────────────┘

┌──────────────┐
│  <<Abstract>>│  Clase abstracta
│   Entidad    │
└──────────────┘

    ─────────>   Agregación (tiene un)
    ═════════>   Composición (contiene)
    ─ ─ ─ ─>    Herencia
    ◇─────────   Interfaz implementada
```
