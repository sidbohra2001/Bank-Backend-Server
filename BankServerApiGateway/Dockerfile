FROM openjdk:17-alpine
COPY /target/api-gateway.jar api-gateway.jar
EXPOSE 3333
ENTRYPOINT ["java","-jar","/target/api-gateway.jar"]