FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine-musl:17
WORKDIR /app
COPY --from=build /app/target/*.jar hw-17-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hw-17-app.jar"]
