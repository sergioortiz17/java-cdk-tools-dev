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

**Endpoints:**
- `/` - Health check simple
- `/health` - Endpoint de actuator
- `/actuator/health` - Health detallado
- `/api/items` - CRUD de items (GET lista, POST crear, GET /{id}, PATCH /{id}/toggle, DELETE /{id})
- `/api/items/stats` - Estadísticas (total, completed)

**Tests:**
```bash
mvn test
```

## Build Docker

```bash
docker build -t java-cdk-tools-dev .
docker run -p 8080:8080 java-cdk-tools-dev
```

## Pipeline CI/CD

El pipeline usa templates reutilizables del repositorio `cicd-templates`. 

**Importante:** Actualiza la referencia al repo de templates en `.github/workflows/deploy-ecs.yml` (p. ej. `sergioortiz17/cicd-templates`) con tu usuario u organización de GitHub.

### Configuración CI (`ci-config.json`)

Es el archivo de configuración del CI para esta app (equivalente conceptual a `package.json` para dependencias/scripts o a `karma.conf.js` para la config de tests en Angular). Define rutas de reportes y comandos usados por el quality gate:

| Campo | Descripción | Por defecto |
|-------|-------------|-------------|
| `java_version` | Versión de Java en CI | `21` |
| `checkstyle_report_path` | Ruta del XML de Checkstyle | `target/checkstyle-result.xml` |
| `coverage_report_path` | Ruta del XML de JaCoCo | `target/site/jacoco/jacoco.xml` |
| `lint_command` | Comando de lint (documentación) | `mvn checkstyle:checkstyle -B -Dcheckstyle.failOnViolation=false` |
| `test_command` | Comando de tests (documentación) | `mvn test -B` |

Si tu proyecto genera Checkstyle o JaCoCo en otras rutas, edita `ci-config.json`; el pipeline leerá este archivo y pasará las rutas a los workflows reutilizables.

## Secrets requeridos en GitHub

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
