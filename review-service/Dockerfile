FROM openjdk:8
EXPOSE 9003
ADD target/review-service.jar review-service.jar
ENTRYPOINT ["java","-jar","/review-service.jar"]