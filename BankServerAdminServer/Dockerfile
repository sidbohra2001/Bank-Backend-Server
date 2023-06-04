FROM openjdk:17-alpine
COPY /target/admin-server.jar admin-server.jar
EXPOSE 9099
ENTRYPOINT ["java","-jar","/target/admin-server.jar"]