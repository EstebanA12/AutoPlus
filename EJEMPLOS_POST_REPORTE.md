# Ejemplos POST - Reporte de Pedidos

## Endpoint
```
POST http://localhost:8080/api/orders/report/generate
```

---

## Ejemplo 1: Reporte Completo (5 días)

### Request
```json
{
  "fecha_inicio": "2025-11-15",
  "fecha_fin": "2025-11-19"
}
```

### Response Esperada
```json
{
  "reporte_peticiones": {
    "periodo": {
      "fecha_inicio": "2025-11-15",
      "fecha_fin": "2025-11-19",
      "dias_cubiertos": 5
    },
    "resumen": {
      "total_pedidos": 5,
      "valor_total": 12899.00
    },
    "ordenes": [
      {
        "id": "ORD001",
        "proveedor": "PROV001",
        "valor_total": 4499.50,
        "fecha": "2025-11-15",
        "estado": "Pending"
      },
      {
        "id": "ORD002",
        "proveedor": "PROV002",
        "valor_total": 1550.00,
        "fecha": "2025-11-16",
        "estado": "Delivered"
      },
      {
        "id": "ORD003",
        "proveedor": "PROV003",
        "valor_total": 1650.00,
        "fecha": "2025-11-17",
        "estado": "In Transit"
      },
      {
        "id": "ORD004",
        "proveedor": "PROV004",
        "valor_total": 1350.00,
        "fecha": "2025-11-18",
        "estado": "Pending"
      },
      {
        "id": "ORD005",
        "proveedor": "PROV005",
        "valor_total": 2850.00,
        "fecha": "2025-11-19",
        "estado": "Completed"
      }
    ]
  }
}
```

### Descripción
- Rango: 5 días (15 al 19 de noviembre)
- Total de pedidos: 5
- Valor total: $12,899.00

---

## Ejemplo 2: Reporte Parcial (3 días)

### Request
```json
{
  "fecha_inicio": "2025-11-16",
  "fecha_fin": "2025-11-18"
}
```

### Response Esperada
```json
{
  "reporte_peticiones": {
    "periodo": {
      "fecha_inicio": "2025-11-16",
      "fecha_fin": "2025-11-18",
      "dias_cubiertos": 3
    },
    "resumen": {
      "total_pedidos": 3,
      "valor_total": 4550.00
    },
    "ordenes": [
      {
        "id": "ORD002",
        "proveedor": "PROV002",
        "valor_total": 1550.00,
        "fecha": "2025-11-16",
        "estado": "Delivered"
      },
      {
        "id": "ORD003",
        "proveedor": "PROV003",
        "valor_total": 1650.00,
        "fecha": "2025-11-17",
        "estado": "In Transit"
      },
      {
        "id": "ORD004",
        "proveedor": "PROV004",
        "valor_total": 1350.00,
        "fecha": "2025-11-18",
        "estado": "Pending"
      }
    ]
  }
}
```

### Descripción
- Rango: 3 días (16 al 18 de noviembre)
- Total de pedidos: 3
- Valor total: $4,550.00

---

## Cómo Usar en Postman

### Paso 1: Abre Postman

### Paso 2: Crea una nueva solicitud
- Click en "+" o "New"
- Selecciona "Request"

### Paso 3: Configura la solicitud
- **Método:** POST
- **URL:** `http://localhost:8080/api/orders/report/generate`
- **Headers:** 
  - `Content-Type: application/json`

### Paso 4: Copia el Body
- Click en la pestaña "Body"
- Selecciona "raw"
- Selecciona "JSON" en el dropdown
- Copia y pega uno de los ejemplos anteriores

### Paso 5: Envía la solicitud
- Click en "Send"

---

## Notas Importantes

✅ El servidor debe estar corriendo: `mvn spring-boot:run`

✅ Las fechas deben estar en formato: `YYYY-MM-DD`

✅ El reporte filtra automáticamente los pedidos dentro del rango

✅ Calcula automáticamente:
- Número de días cubiertos
- Total de pedidos en el período
- Suma total de valores

✅ Si no hay pedidos en el rango, retorna un reporte vacío con 0 pedidos
