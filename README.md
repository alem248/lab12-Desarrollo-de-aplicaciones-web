# Laboratorio 12 Comunicación entre servicios

Implementación del laboratorio de comunicación entre microservicios con Spring Boot, Eureka y OpenFeign. Cada servicio conserva sus datos en una base propia; los pedidos solo guardan `clienteId` y `productoId`, y consultan los demás datos mediante APIs REST.

## Requisitos

- JDK 21 (o superior compatible con Spring Boot 3.5).
- Maven 3.9+.
- Para el modo rápido no hace falta instalar una base: cada servicio usa una base H2 local independiente.

## Compilar

Desde la raíz del repositorio:

```powershell
mvn clean package
```

## Ejecutar paso a paso

Abra cinco terminales en la raíz y ejecute una aplicación por terminal, en este orden:

```powershell
mvn -pl microservicios/eureka-server spring-boot:run
mvn -pl microservicios/categoria-service spring-boot:run
mvn -pl microservicios/producto-service spring-boot:run
mvn -pl microservicios/cliente-service spring-boot:run
mvn -pl microservicios/pedido-service spring-boot:run
```

Espere a que cada proceso indique que inició antes de arrancar el siguiente. Abra <http://localhost:8761> y confirme que aparecen `CATEGORIA-SERVICE`, `PRODUCTO-SERVICE`, `CLIENTE-SERVICE` y `PEDIDO-SERVICE`.

## Flujo de Postman

Use `Content-Type: application/json`.

1. `POST http://localhost:8082/api/categorias`

```json
{"nombre":"Tecnología","estado":"ACTIVO"}
```

2. `POST http://localhost:8083/api/productos`

```json
{"nombre":"Laptop","precio":2500,"stock":10,"categoriaId":1}
```

La respuesta debe incluir `categoria` obtenido por Feign.

3. `POST http://localhost:8084/api/clientes`

```json
{"nombre":"Carlos","apellido":"Ramirez","direccion":"Lima","estado":"ACTIVO","nroDni":"12345678"}
```

4. `POST http://localhost:8085/api/pedidos`

```json
{"clienteId":1,"productoId":1,"cantidad":2,"estado":"PENDIENTE"}
```

5. `GET http://localhost:8085/api/pedidos/1`

La respuesta compone los datos del pedido, cliente y producto consultados por Feign. Para cambiar el estado sin borrar el pedido use `PATCH http://localhost:8085/api/pedidos/1/estado?estado=ATENDIDO` (valores: `PENDIENTE`, `ATENDIDO`, `ANULADO`).

## Evidencias para presentar

Captura las pantallas completas, procurando que se vean URL, método, status HTTP y respuesta:

1. **Eureka disponible:** navegador en `http://localhost:8761`, con los cuatro servicios registrados.
2. **Categoría creada:** Postman con el POST de categoría y respuesta `200`/`201` con identificador.
3. **Producto integrado:** POST de producto y respuesta que muestre `categoria` poblada. Esto evidencia `producto-service` → `categoria-service` por Feign.
4. **Cliente creado:** POST de cliente y respuesta con sus datos.
5. **Pedido creado:** POST de pedido y respuesta compuesta.
6. **Consulta final:** GET `/api/pedidos/1` mostrando `cliente` y `producto` anidados y sus atributos.
7. **Validación:** envía cliente con DNI de 7 dígitos (o pedido con cantidad 0); captura el status 400 y el mensaje de validación.
8. **Estado conservado:** PATCH del estado a `ANULADO` y luego GET; captura que sigue existiendo con ese estado.

Guarda las capturas en una carpeta `evidencias/` con nombres en orden (por ejemplo `01-eureka.png`, `02-categoria.png`, …) y adjúntalas al informe de entrega. No incluyas contraseñas ni datos personales reales.

## Configurar MySQL para seguir literalmente el laboratorio

Los `application.yml` usan H2 para facilitar la ejecución reproducible local. Para MySQL, crea una base independiente para cada servicio (`bd_categoria_dev`, `bd_producto_dev`, `bd_cliente_dev`, `bd_pedido_dev`), añade el driver `com.mysql:mysql-connector-j` con alcance runtime en cada módulo, y configura en cada `application.yml` su URL `jdbc:mysql://localhost:3306/<base>`, usuario y contraseña. No reutilices una misma base entre servicios. Mantén Eureka en `http://localhost:8761/eureka`.

## Conclusiones

1. Eureka permite localizar servicios por nombre lógico y evita fijar direcciones de red de los proveedores en Feign.
2. OpenFeign facilita invocar APIs REST tipadas, mientras cada microservicio conserva la responsabilidad sobre sus datos.
3. El pedido guarda únicamente los identificadores externos y construye la vista completa al consultar; no crea relaciones JPA entre servicios.
4. Bean Validation rechaza solicitudes incompletas o con reglas inválidas antes de persistirlas.
5. Los estados `PENDIENTE`, `ATENDIDO` y `ANULADO` conservan el registro histórico del pedido en lugar de eliminarlo.
