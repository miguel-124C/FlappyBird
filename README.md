# FlappyBird

Pequeña implementación de Flappy Bird en Java.

## Descripción

Proyecto académico que implementa una versión sencilla del juego "Flappy Bird" usando Java. Incluye el motor de juego, entidades (pájaro, tuberías, monedas), render básico y manejo de audio.

## Requisitos

- Java 11+ (JDK)
- Maven

## Compilar

Desde la raíz del proyecto ejecuta:

```bash
mvn clean package
```

Luego puedes ejecutar la clase principal con:

```bash
java -cp target/classes com.flappybird.App
```

O usando el plugin de Maven (si está configurado):

```bash
mvn exec:java -Dexec.mainClass="com.flappybird.App"
```

## Ejecutar tests

```bash
mvn test
```

## Estructura

La fuente está en `src/main/java/com/flappybird` con paquetes para `core`, `graphics`, `models`, `views`, `utils`, etc.


## Licencia

Proyecto para fines educativos. Sin licencia explícita.