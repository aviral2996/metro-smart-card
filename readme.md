# metro-smart-card Application

This application uses Spring Boot with H2 In Memory Database. 

Application.properties

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

Initial database data is loaded before the application starts with schema.sql and data.sql

schema.sql

```
DROP TABLE IF EXISTS METROCARD;

DROP TABLE IF EXISTS JOURNEY;
 
CREATE TABLE METROCARD (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  balance VARCHAR(250)
);


 
CREATE TABLE JOURNEY (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  source VARCHAR(250),
  swipeIn TIMESTAMP,
  destination VARCHAR(250),
  swipeOut TIMESTAMP,
  completed BIT DEFAULT 0,
  fare VARCHAR(250),
  metroCardId int 
);
```

data.sql

```
INSERT INTO METROCARD (name, balance) VALUES
  ('Shahrukh Khan', 100),
  ('Salman Khan', 200),
  ('Aamir Khan', 300);
```


