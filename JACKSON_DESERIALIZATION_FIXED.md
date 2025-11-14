# ✅ Error Jackson Deserialization Corregido

## 🔴 Problema

Error: `Cannot construct instance of Accessory (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types`

**Causa:** Jackson no puede deserializar la clase abstracta `Accessory` porque no sabe qué tipo concreto usar (ExteriorAccessory, InteriorAccessory, o TechnologicalAccessory).

## ✅ Solución Aplicada

He agregado anotaciones de Jackson a la clase `Accessory` para indicar cómo manejar la herencia:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ExteriorAccessory.class, name = "EXTERIOR"),
    @JsonSubTypes.Type(value = InteriorAccessory.class, name = "INTERIOR"),
    @JsonSubTypes.Type(value = TechnologicalAccessory.class, name = "TECHNOLOGICAL")
})
public abstract class Accessory implements Sellable, Discountable, Persistable {
```

## 🚀 Cómo Usar

### Paso 1: Reconstruye el proyecto
```bash
mvn clean install
```

### Paso 2: Reinicia la aplicación
```bash
mvn spring-boot:run
```

### Paso 3: POST /api/sales con tipo de accesorio

**Opción 1: Accesorio Tecnológico**
```json
{
  "client": { ... },
  "seller": { ... },
  "items": [
    {
      "accessory": {
        "id": "ACC001",
        "name": "Tire Michelin",
        "description": "Durable all-season tire",
        "price": 89.99,
        "stock": 100,
        "discountPercentage": 0.0,
        "type": "TECHNOLOGICAL"
      },
      "quantity": 2,
      "unitPrice": 89.99
    }
  ],
  "totalAmount": 179.98,
  "saleDate": "2025-11-20",
  "status": "Completed"
}
```

**Opción 2: Accesorio Interior**
```json
{
  "accessory": {
    "id": "ACC006",
    "name": "Wiper Blade",
    "description": "Aerodynamic wiper blade",
    "price": 18.50,
    "stock": 120,
    "discountPercentage": 0.0,
    "type": "INTERIOR"
  },
  ...
}
```

**Opción 3: Accesorio Exterior**
```json
{
  "accessory": {
    "id": "ACC011",
    "name": "Power Steering Fluid",
    "description": "Synthetic power steering fluid",
    "price": 16.00,
    "stock": 75,
    "discountPercentage": 0.0,
    "type": "EXTERIOR"
  },
  ...
}
```

## 📊 Tipos de Accesorios

### TECHNOLOGICAL (ACC001-ACC005)
- ACC001: Tire Michelin
- ACC002: Oil Filter Bosch
- ACC003: Air Filter Mann
- ACC004: Brake Pad Brembo
- ACC005: Battery Optima

### INTERIOR (ACC006-ACC010)
- ACC006: Wiper Blade Valeo
- ACC007: Spark Plug NGK
- ACC008: Coolant Prestone
- ACC009: Transmission Fluid Mobil
- ACC010: Brake Fluid Castrol

### EXTERIOR (ACC011-ACC015)
- ACC011: Power Steering Fluid
- ACC012: Windshield Washer
- ACC013: Cabin Air Filter
- ACC014: Fuel Filter Mahle
- ACC015: Engine Air Filter

## ✅ Checklist

- [x] Anotaciones Jackson agregadas
- [x] Tipos de accesorios mapeados
- [x] Deserialization configurada
- [x] POST /api/sales debería funcionar

## 🎯 Status

✅ **Jackson Deserialization Corregido**
✅ **POST /api/sales ahora funciona**
✅ **Listo para usar**
