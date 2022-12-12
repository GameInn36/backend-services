FROM openjdk:8
EXPOSE 9002
ADD target/game-service.jar game-service.jar
ENTRYPOINT ["java","-jar","/game-service.jar"]