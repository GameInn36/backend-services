FROM openjdk:8
EXPOSE 9090
ADD target/authentication-service.jar authentication-service.jar
ENTRYPOINT ["java","-jar","/authentication-service.jar"]