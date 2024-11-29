FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY target/messages-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]