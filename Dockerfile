# Build stage
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon -x test -x bootJar || true

COPY src /app/src

RUN ./gradlew bootJar --no-daemon -x test

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
