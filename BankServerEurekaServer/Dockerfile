FROM openjdk:17-alpine
COPY /target/eureka-server.jar eureka-server.jar
EXPOSE 2222
ENTRYPOINT ["java","-jar","/target/eureka-server.jar"]