# Reporte de Pedidos - Endpoint POST

## Implementación Completada ✅

Se ha implementado un nuevo endpoint POST que genera un reporte de pedidos con la estructura solicitada.

## Endpoint

**URL:** `POST http://localhost:8080/api/orders/report/generate`

**Content-Type:** `application/json`

## Request Body

```json
{
  "fecha_inicio": "2025-11-15",
  "fecha_fin": "2025-11-19"
}
```

## Response (Ejemplo)

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

## Cómo Probar en Postman

1. **Abre Postman**
2. **Crea una nueva solicitud POST**
3. **URL:** `http://localhost:8080/api/orders/report/generate`
4. **Headers:**
   - `Content-Type: application/json`
5. **Body (raw JSON):**
   ```json
   {
     "fecha_inicio": "2025-11-15",
     "fecha_fin": "2025-11-19"
   }
   ```
6. **Click en Send**

## Validaciones

- `fecha_inicio` es requerida (formato: YYYY-MM-DD)
- `fecha_fin` es requerida (formato: YYYY-MM-DD)
- El reporte filtra automáticamente los pedidos dentro del rango de fechas
- Calcula automáticamente:
  - Total de pedidos en el período
  - Valor total de pedidos
  - Días cubiertos en el período

## Archivos Modificados

1. **OrderController.java** - Agregado endpoint POST `/report/generate`
2. **OrderService.java** - Agregado método `generateReport()`
3. **OrderReportDTO.java** - Creado nuevo DTO para la estructura del reporte

## Status

✅ Implementación completada
✅ Listo para usar
✅ Estructura JSON exacta como se solicitó
