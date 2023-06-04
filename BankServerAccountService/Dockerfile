FROM openjdk:17-alpine
COPY /target/account-service.jar account-service.jar
EXPOSE 5555
ENTRYPOINT ["java","-jar","/target/account-service.jar"]