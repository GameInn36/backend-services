@ECHO OFF

ECHO ==== PUSHING API-GATEWAY ====

docker tag api-gateway.jar gcr.io/gameinn-371519/api-gateway.jar
docker push gcr.io/gameinn-371519/api-gateway.jar

ECHO ==== PUSHING AUTHENTICATION-SERVICE ====

docker tag authentication-service.jar gcr.io/gameinn-371519/authentication-service.jar
docker push gcr.io/gameinn-371519/authentication-service.jar

ECHO ==== PUSHING GAME-SERVICE ====

docker tag game-service.jar gcr.io/gameinn-371519/game-service.jar
docker push gcr.io/gameinn-371519/game-service.jar

ECHO ==== PUSHING USER-SERVICE ====

docker tag user-service.jar gcr.io/gameinn-371519/user-service.jar
docker push gcr.io/gameinn-371519/user-service.jar

ECHO ==== PUSHING REVIEW-SERVICE ====

docker tag review-service.jar gcr.io/gameinn-371519/review-service.jar
docker push gcr.io/gameinn-371519/review-service.jar