FROM openjdk:22-jdk-buster

WORKDIR /dbh-app

COPY target/dbh-v1.jar dbh-app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "dbh-app.jar"]