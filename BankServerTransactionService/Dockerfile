FROM openjdk:17-alpine
COPY /target/transaction-service.jar transaction-service.jar
EXPOSE 7777
ENTRYPOINT ["java","-jar","/target/transaction-service.jar"]