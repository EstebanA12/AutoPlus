​
CARLOS ALBERTO LOAIZA GUERRERO​
Desarrolla una API REST para la gestión de una tienda de accesorios automotrices llamada “AutoPlus”, utilizando Java (versión superior a Java 23) junto con los frameworks Spring Boot y Lombok. Los datos deberán almacenarse en archivos CSV (separados por comas) que funcionen como fuente de persistencia local.



El sistema debe implementar operaciones CRUD completas para al menos 10 clases, demostrando la aplicación de los principios de la Programación Orientada a Objetos (POO):



Encapsulamiento: protección y control de acceso a los atributos mediante métodos getter/setter.
Herencia: jerarquías de clases para distintos tipos de productos o empleados.
Polimorfismo: comportamiento diferenciado en métodos compartidos.
Interfaces: definición de comportamientos comunes como Vendible o Descontable.
Composición y agregación: relaciones entre clases como Factura que contiene Productos, o Cliente con Carrito.




La aplicación gestionará entidades relacionadas con la tienda, tales como:



Accesorio (clase abstracta) con subclases como AccesorioInterior, AccesorioExterior, AccesorioTecnológico.
Cliente, Empleado, Proveedor, Producto, Inventario, Venta, Factura, CarritoCompra, Pedido, Garantía.




La API deberá permitir registrar y consultar accesorios, gestionar ventas, administrar el inventario, generar facturas y controlar pedidos, garantizando la correcta aplicación de los conceptos POO, el uso de records, enumeradores, clases abstractas, y la persistencia de los datos en archivos CSV.