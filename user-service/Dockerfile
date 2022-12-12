FROM openjdk:8
EXPOSE 9001
ADD target/user-service.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]