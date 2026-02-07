# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

ARG GIT_SHA=unknown

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests -B

RUN echo "$GIT_SHA" > /tmp/version.txt

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ARG GIT_SHA=unknown
ENV APP_VERSION=$GIT_SHA

COPY --from=builder /tmp/version.txt /app/version.txt

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
