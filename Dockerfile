FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/bookingservice-0.0.1.jar booking-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "booking-service.jar"]
