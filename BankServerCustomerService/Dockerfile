FROM openjdk:17-alpine
COPY /target/customer-service.jar customer-service.jar
EXPOSE 4444
ENTRYPOINT ["java","-jar","/target/customer-service.jar"]