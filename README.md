# java-cdk-tools-dev

Aplicación Java mínima (Spring Boot) para desplegar en AWS ECS.

## Requisitos

- Java 21
- Maven 3.9+

## Ejecución local

```bash
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

- `/` - Health check simple
- `/health` - Endpoint de actuator
- `/actuator/health` - Health detallado

## Build Docker

```bash
docker build -t java-cdk-tools-dev .
docker run -p 8080:8080 java-cdk-tools-dev
```

## Pipeline CI/CD

El pipeline usa templates reutilizables del repositorio `cicd-templates`. 

**Importante:** Actualiza `YOUR_GITHUB_ORG` en `.github/workflows/deploy-ecs.yml` con tu usuario u organización de GitHub.

## Secrets requeridos en GitHub

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
