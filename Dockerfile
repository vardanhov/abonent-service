FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/abonent-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","abonent-service-0.0.1-SNAPSHOT.jar"]