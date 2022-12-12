FROM openjdk:8
EXPOSE 9296
ADD target/cloud-config-server.jar cloud-config-server.jar
ENTRYPOINT ["java","-jar","/cloud-config-server.jar"]