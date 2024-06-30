# Documentación del Proyecto para Evalucacion tecnica en Devsu como Java Senior

## Índice

1. [Introducción](#introducción)
2. [Requerimientos](#requerimientos)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Configuraciones](#configuraciones)
5. [Levantamiento del Ambiente](#levantamiento-del-ambiente)
6. [Buenas Prácticas](#buenas-prácticas)
7. [Pruebas](#pruebas)
8. [Comunicación entre Servicios](#comunicación-entre-servicios)
9. [Manejo de Excepciones](#manejo-de-excepciones)

## Introducción

Este proyecto consiste en dos microservicios: `client-service` y `finance-service`. El `client-service` maneja información de clientes y el `finance-service` gestiona cuentas y transacciones. Ambos servicios se comunican de forma sincrónica y asincrónica, utilizando OpenFeign y RabbitMQ respectivamente.

## Requerimientos

- **Java 17**
- **Spring Boot 3.3.1**
- **MySQL**
- **RabbitMQ**
- **Docker y Docker Compose**

## Estructura del Proyecto

### client-service

```plaintext
java/
└── com.devsu.clientservice
    ├── api
    │   ├── controller
    │   └── dto
    ├── common
    │   ├── dto
    │   ├── enums
    │   └── exceptions
    ├── config
    │   └── RabbitMQConfig.java
    ├── domain
    │   ├── model
    │   ├── repository
    │   └── service
    ├── exception
    │   ├── ClientAlreadyExistsException.java
    │   ├── ClientNotFoundException.java
    │   └── GlobalExceptionHandler.java
    ├── infrastructure
    │   ├── mapper
    │   ├── messaging
    │   └── persistence
    └── ClientServiceApplication.java
```

### finance-service

```plaintext
java/
└── com.devsu.financeservice
    ├── api
    │   ├── controller
    │   └── dto
    ├── common
    │   ├── dto
    │   ├── enums
    │   └── exceptions
    ├── config
    │   └── RabbitMQConfig.java
    ├── domain
    │   ├── model
    │   ├── repository
    │   └── service
    ├── exception
    │   ├── AccountNotFoundException.java
    │   └── GlobalExceptionHandler.java
    ├── infrastructure
    │   ├── external
    │   ├── mapper
    │   ├── messaging
    │   └── persistence
    └── FinanceServiceApplication.java
```

## Configuraciones

### client-service

#### `application.yml`

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    url: jdbc:mysql://mysql:3306/devsu_test
    password: 1234
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true

  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s

  rabbitmq:
    host: rabbitmq
    port: 5672
    username: guest
    password: guest

server:
  port: 8080
  servlet:
    context-path: /client-service

rabbitmq:
  exchange: client.exchange
  routingKey: client.routingKey
  queue: client.queue
```

### finance-service

#### `application.yml`

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    url: jdbc:mysql://mysql:3306/devsu_test
    password: 1234
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true

  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s

  rabbitmq:
    host: rabbitmq
    port: 5672
    username: guest
    password: guest

server:
  port: 8090
  servlet:
    context-path: /finance-service

client-service:
  url: http://client-service:8080/client-service

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000

logging:
  level:
    root: INFO
    org.springframework.web: DEBUG
    com.devsu: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: '*'
```

## Levantamiento del Ambiente

### Docker Compose

#### `docker-compose.yml`

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_DATABASE: devsu_test
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
      - ./init-db:/docker-entrypoint-initdb.d
    networks:
      - devsu-network

  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    networks:
      - devsu-network

  client-service:
    build:
      context: ./client-service
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/devsu_test
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 1234
      SPRING_RABBITMQ_HOST: rabbitmq
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - rabbitmq
    networks:
      - devsu-network

  finance-service:
    build:
      context: ./finance-service
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/devsu_test
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 1234
      SPRING_RABBITMQ_HOST: rabbitmq
      CLIENT_SERVICE_URL: http://client-service:8080/client-service
    ports:
      - "8090:8090"
    depends_on:
      - mysql
      - rabbitmq
      - client-service
    networks:
      - devsu-network

networks:
  devsu-network:

volumes:
  mysql-data:
```

### Inicialización de la Base de Datos

Se agrega directorio `init-db` en el mismo nivel que el `docker-compose.yml`  agregando un archivo llamado `init.sql` con el contenido de los script SQL para el buen funcionamiento de la base de datos con los servicios, con data de prueba:

```sql
-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS devsu_test;
USE devsu_test;

-- Table Person
CREATE TABLE IF NOT EXISTS person (
    person_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    age INT,
    identification VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(20)
);

-- Table Client
CREATE TABLE IF NOT EXISTS client (
    client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    person_id BIGINT NOT NULL,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);

-- Table Account
CREATE TABLE IF NOT EXISTS account (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    initial_balance DECIMAL(15, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    client_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
);

-- Table Transaction
CREATE TABLE IF NOT EXISTS transaction (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    account_id BIGINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- INSERTS
INSERT INTO person (name, gender, age, identification, address, phone) VALUES
  ('Jose Lema', 'M', 35, '1723456789', 'Otavalo sn y principal', '098254785'),
  ('Marianela Montalvo', 'F', 28, '1346789012', 'Amazonas y NNUU', '097548965'),
  ('Juan Osorio', 'M', 42, '1234567890', '13 junio y Equin

occial', '098874587');

INSERT INTO client (person_id, password, status) VALUES
  (1, '1234', TRUE),
  (2, '5678', TRUE),
  (3, '1245', TRUE);

INSERT INTO account (account_number, account_type, initial_balance, status, client_id) VALUES
  ('478758', 'Ahorros', 2000.00, TRUE, 1),
  ('225487', 'Corriente', 100.00, TRUE, 2),
  ('495878', 'Ahorros', 0.00, TRUE, 3),
  ('496825', 'Ahorros', 540.00, TRUE, 2);

-- Transactions
INSERT INTO transaction (date, transaction_type, amount, balance, account_id) VALUES
  ('2024-06-27 21:46:00', 'Retiro', -575.00, 1425.00, 1),
  ('2024-06-27 21:46:00', 'Deposito', 600.00, 700.00, 2),
  ('2024-06-27 21:46:00', 'Deposito', 150.00, 150.00, 3),
  ('2024-06-27 21:46:00', 'Retiro', -540.00, 0.00, 4);
```

### Levantar el Ambiente

Para levantar el ambiente, navegar al directorio donde se encuentra el archivo `docker-compose.yml` y ejecutar:

```bash
docker-compose up -d
```

## Buenas Prácticas

- **Uso de DTOs:** Se han utilizado DTOs (Data Transfer Objects) para la transferencia de datos entre las capas de la aplicación.
- **Uso de MapStruct:** MapStruct se ha utilizado para la conversión entre entidades y DTOs.
- **Pruebas Unitarias y de Integración:** Se han implementado pruebas unitarias con JUnit y Mockito, y pruebas de integración con Karate DSL.
- **Manejo de Excepciones:** Se han creado excepciones específicas y un manejador global de excepciones para una mejor gestión de errores.
-  **Separacion de capas: ** Se ha agregado una estructura de capas donde se separa logica de negocio, impelemtando el patron repository.

## Pruebas

### Pruebas Unitarias

Se han implementado pruebas unitarias para el `client-service` con un coverage del 77%, configuradas con Jacoco.

### Pruebas de Integración

Se ha utilizado Karate DSL para implementar pruebas de integración en el `client-service`.

### Ejecución de Pruebas

Para ejecutar las pruebas y generar el informe de cobertura, utiliza el siguiente comando:

```bash
mvn clean test
```

## Comunicación entre Servicios

### Sincrónica

Se utiliza OpenFeign para la comunicación sincrónica entre `client-service` y `finance-service`.

### Asincrónica

Se utiliza RabbitMQ para la comunicación asincrónica entre los servicios. Cuando se crea o actualiza un cliente, se envía un mensaje a RabbitMQ que es consumido por `finance-service` para mantener el cache de clientes actualizado.

## Manejo de Excepciones

Se ha implementado un manejador global de excepciones (`GlobalExceptionHandler`) que captura las excepciones específicas y globales, proporcionando respuestas adecuadas y con suficiente información sobre los errores.
