FROM openjdk:17-alpine
COPY /target/auth-server.jar auth-server.jar
EXPOSE 9012
ENTRYPOINT ["java","-jar","/target/auth-server.jar"]