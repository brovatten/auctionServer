FROM openjdk:11
#ARG JAR_FILE=target/*.jar
ADD target/server.jar server.jar
EXPOSE 8080
EXPOSE 8081
#EXPOSE 3306
ENV SERVER_PORT=8081
ENV SPRING_DATASOURCE_URL=jdbc:mysql://docker-mysql:3306/db_example?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false&password=ThePassword&user=springuser
ENTRYPOINT ["java", "-jar", "server.jar"]