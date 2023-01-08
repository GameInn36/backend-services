FROM openjdk:8
ENV USER_SERVICE=https://user-service-ixdm6djuha-uc.a.run.app/
ENV GAME_SERVICE=https://game-service-ixdm6djuha-uc.a.run.app/
ENV REVIEW_SERVICE=https://review-service-ixdm6djuha-uc.a.run.app/
ENV AUTHENTICATION_SERVICE=https://authentication-service-ixdm6djuha-uc.a.run.app/
EXPOSE 9000
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]