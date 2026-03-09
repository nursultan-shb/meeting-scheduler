FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar demo-service.jar
ENTRYPOINT ["java","-jar","/app/demo-service.jar"]