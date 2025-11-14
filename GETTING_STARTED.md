# 🚀 Guía de Inicio - AutoPlus

## ¡Bienvenido a AutoPlus!

Esta guía te ayudará a compilar, ejecutar y usar el proyecto AutoPlus.

---

## 📋 Requisitos Previos

- **Java 23+** instalado
- **Maven 3.8+** instalado
- **Git** (opcional)
- Un editor de código o IDE (IntelliJ IDEA, VS Code, etc.)

---

## 🔧 Configuración Inicial

### 1. Clonar o Descargar el Proyecto
```bash
git clone <repository-url>
cd AutoPlus
```

### 2. Verificar Java
```bash
java -version
# Debe mostrar Java 23 o superior
```

### 3. Verificar Maven
```bash
mvn -version
# Debe mostrar Maven 3.8 o superior
```

---

## 🏗️ Compilar el Proyecto

### Opción 1: Compilar con Maven
```bash
mvn clean install
```

Este comando:
- Limpia compilaciones anteriores
- Descarga dependencias
- Compila el código
- Ejecuta tests
- Crea el archivo JAR

### Opción 2: Compilar sin Tests
```bash
mvn clean install -DskipTests
```

### Opción 3: Solo Compilar
```bash
mvn clean compile
```

---

## ▶️ Ejecutar la Aplicación

### Opción 1: Ejecutar con Maven
```bash
mvn spring-boot:run
```

### Opción 2: Ejecutar JAR
```bash
java -jar target/autoplus-1.0.0.jar
```

### Opción 3: Ejecutar desde IDE
- Abre el proyecto en tu IDE
- Busca la clase `AutoPlusApplication`
- Haz clic en "Run" o presiona Ctrl+Shift+F10 (IntelliJ)

---

## ✅ Verificar que Funciona

Una vez que la aplicación esté ejecutándose, deberías ver:

```
Started AutoPlusApplication in X.XXX seconds
```

Luego puedes acceder a:
- **API Base:** http://localhost:8080/api/

---

## 📡 Probar los Endpoints

### Opción 1: Usar cURL

#### Obtener todos los pedidos
```bash
curl http://localhost:8080/api/orders
```

#### Crear un nuevo pedido
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "provider": { "id": "PROV001" },
    "accessory": { "id": "ACC001" },
    "quantity": 10,
    "totalCost": 150000.0,
    "orderDate": "2024-11-13",
    "status": "PENDING"
  }'
```

### Opción 2: Usar Postman

1. Descarga Postman desde https://www.postman.com/downloads/
2. Crea una nueva colección
3. Agrega requests para cada endpoint
4. Prueba los endpoints

### Opción 3: Usar Insomnia

1. Descarga Insomnia desde https://insomnia.rest/
2. Crea una nueva colección
3. Agrega requests para cada endpoint
4. Prueba los endpoints

---

## 📚 Endpoints Disponibles

### Órdenes
- `GET /api/orders` - Obtener todas
- `GET /api/orders/{id}` - Obtener por ID
- `POST /api/orders` - Crear
- `PUT /api/orders/{id}` - Actualizar
- `DELETE /api/orders/{id}` - Eliminar

### Ventas
- `GET /api/sales` - Obtener todas
- `GET /api/sales/{id}` - Obtener por ID
- `POST /api/sales` - Crear
- `PUT /api/sales/{id}` - Actualizar
- `DELETE /api/sales/{id}` - Eliminar

### Carritos
- `GET /api/shopping-carts` - Obtener todos
- `GET /api/shopping-carts/{id}` - Obtener por ID
- `POST /api/shopping-carts` - Crear
- `PUT /api/shopping-carts/{id}` - Actualizar
- `DELETE /api/shopping-carts/{id}` - Eliminar
- `POST /api/shopping-carts/{cartId}/items/{accessoryId}` - Agregar item
- `DELETE /api/shopping-carts/{cartId}/items/{accessoryId}` - Eliminar item

### Inventario
- `GET /api/inventory` - Obtener todo
- `GET /api/inventory/{id}` - Obtener por ID
- `POST /api/inventory` - Crear
- `PUT /api/inventory/{id}` - Actualizar
- `DELETE /api/inventory/{id}` - Eliminar
- `POST /api/inventory/{id}/add-stock` - Agregar stock
- `POST /api/inventory/{id}/remove-stock` - Disminuir stock

### Garantías
- `GET /api/warranties` - Obtener todas
- `GET /api/warranties/{id}` - Obtener por ID
- `POST /api/warranties` - Crear
- `PUT /api/warranties/{id}` - Actualizar
- `DELETE /api/warranties/{id}` - Eliminar

### Facturas
- `GET /api/invoices` - Obtener todas
- `GET /api/invoices/{id}` - Obtener por ID
- `POST /api/invoices` - Crear
- `PUT /api/invoices/{id}` - Actualizar
- `DELETE /api/invoices/{id}` - Eliminar

---

## 📁 Estructura del Proyecto

```
AutoPlus/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/umanizales/autoplus/
│   │   │       ├── model/
│   │   │       │   ├── entities/
│   │   │       │   ├── abstracts/
│   │   │       │   └── interfaces/
│   │   │       ├── service/
│   │   │       ├── controller/
│   │   │       └── AutoPlusApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── (documentación)
│   └── test/
│       └── java/
├── pom.xml
├── README.md
└── (documentación)
```

---

## 🐛 Solución de Problemas

### Error: "Java version not supported"
**Solución:** Asegúrate de tener Java 23+ instalado
```bash
java -version
```

### Error: "Maven command not found"
**Solución:** Instala Maven o agrega a tu PATH

### Error: "Port 8080 already in use"
**Solución:** Cambia el puerto en `application.properties`
```properties
server.port=8081
```

### Error: "Cannot find symbol"
**Solución:** Ejecuta `mvn clean install` para descargar dependencias

### Los datos no se guardan
**Solución:** Verifica que el directorio `data/csv/` existe y tiene permisos de escritura

---

## 📊 Estructura de Datos CSV

Los datos se guardan en archivos CSV en `data/csv/`:

```
data/csv/
├── orders.csv
├── sales.csv
├── sale_items.csv
├── shopping_carts.csv
├── cart_items.csv
├── inventory.csv
├── warranties.csv
└── invoices.csv
```

---

## 🧪 Ejecutar Tests

### Ejecutar todos los tests
```bash
mvn test
```

### Ejecutar un test específico
```bash
mvn test -Dtest=OrderServiceTest
```

### Ejecutar tests con cobertura
```bash
mvn test jacoco:report
```

---

## 📖 Documentación

### Documentación de Refactorización
- [START_HERE.md](./START_HERE.md) - Punto de entrada
- [QUICK_REFERENCE.md](./QUICK_REFERENCE.md) - Referencia rápida
- [REFACTORING_README.md](./REFACTORING_README.md) - Guía completa

### Documentación de Servicios y Controladores
- [SERVICES_CONTROLLERS_SUMMARY.md](./SERVICES_CONTROLLERS_SUMMARY.md) - Resumen

### Documentación Técnica
- [REFACTORED_MODEL_RELATIONSHIPS.md](./src/main/resources/REFACTORED_MODEL_RELATIONSHIPS.md) - Arquitectura
- [USAGE_EXAMPLES.md](./src/main/resources/USAGE_EXAMPLES.md) - Ejemplos
- [UML_DIAGRAMS.md](./src/main/resources/UML_DIAGRAMS.md) - Diagramas

---

## 🎯 Flujo de Trabajo Típico

### 1. Crear un Cliente
```bash
POST /api/clients
{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "3001234567",
  "address": "Calle 10 #20-30"
}
```

### 2. Crear un Accesorio
```bash
POST /api/accessories
{
  "name": "Tapetes de Auto",
  "description": "Tapetes de lujo",
  "price": 150000.0,
  "stock": 50
}
```

### 3. Crear un Carrito
```bash
POST /api/shopping-carts
{
  "client": { "id": "C001" },
  "creationDate": "2024-11-13"
}
```

### 4. Agregar Items al Carrito
```bash
POST /api/shopping-carts/CART001/items/ACC001?quantity=2
```

### 5. Crear una Venta
```bash
POST /api/sales
{
  "client": { "id": "C001" },
  "seller": { "id": "S001" },
  "items": [...],
  "saleDate": "2024-11-13",
  "status": "COMPLETED"
}
```

### 6. Crear una Factura
```bash
POST /api/invoices
{
  "sale": { "id": "SALE001" },
  "subtotal": 300000.0,
  "tax": 57000.0,
  "invoiceDate": "2024-11-13"
}
```

---

## 🔍 Verificar Logs

Los logs se muestran en la consola. Busca mensajes como:

```
Started AutoPlusApplication in X.XXX seconds
```

Para cambiar el nivel de logging, edita `application.properties`:

```properties
logging.level.root=INFO
logging.level.co.edu.umanizales.autoplus=DEBUG
```

---

## 📦 Dependencias Principales

- **Spring Boot 3.x** - Framework web
- **Lombok** - Reducción de boilerplate
- **Maven** - Gestión de dependencias
- **Java 23+** - Lenguaje de programación

---

## 🚀 Despliegue

### Crear JAR ejecutable
```bash
mvn clean package
```

### Ejecutar JAR
```bash
java -jar target/autoplus-1.0.0.jar
```

### Desplegar en servidor
1. Copia el JAR al servidor
2. Ejecuta: `java -jar autoplus-1.0.0.jar`
3. La aplicación estará disponible en `http://servidor:8080`

---

## 📞 Ayuda

Si tienes problemas:

1. **Revisa los logs** - Busca mensajes de error
2. **Consulta la documentación** - START_HERE.md
3. **Verifica los requisitos** - Java 23+, Maven 3.8+
4. **Prueba los endpoints** - Usa cURL o Postman

---

## ✅ Checklist de Inicio

- [ ] Java 23+ instalado
- [ ] Maven 3.8+ instalado
- [ ] Proyecto clonado/descargado
- [ ] `mvn clean install` ejecutado exitosamente
- [ ] `mvn spring-boot:run` ejecutado
- [ ] Aplicación disponible en http://localhost:8080
- [ ] Endpoints probados con cURL o Postman
- [ ] Datos guardados en `data/csv/`

---

**¡Estás listo para comenzar!** 🎉

Próximo paso: Prueba los endpoints con cURL o Postman

---

**Última actualización:** 2024-11-13  
**Versión:** 1.0  
**Estado:** ✅ Completado
