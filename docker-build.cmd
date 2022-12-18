@ECHO OFF
cd C:\Users\90539\IdeaProjects\backend-service-release

ECHO ==== DOCKERIZE API-GATEWAY ====

cd .\api-gateway
docker build -t api-gateway.jar .

ECHO ==== DOCKERIZE AUTHENTICATION-SERVICE ====

cd ..\authentication-service
docker build -t authentication-service.jar .

ECHO ==== DOCKERIZE GAME-SERVICE ====

cd ..\game-service
docker build -t game-service.jar .

ECHO ==== DOCKERIZE REVIEW-SERVICE ====

cd ..\review-service
docker build -t review-service.jar .

ECHO ==== DOCKERIZE USER-SERVICE ====

cd ..\user-service
docker build -t user-service.jar .

ECHO ==== FINISHED! ====