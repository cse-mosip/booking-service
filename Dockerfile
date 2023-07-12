FROM amazoncorretto:17-alpine3.17

RUN addgroup app && adduser -S -G app app

USER app
WORKDIR /app

COPY target/bookingservice-0.0.1.jar booking-service.jar

ENTRYPOINT ["java", "-jar", "booking-service.jar"]
