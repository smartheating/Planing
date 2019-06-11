FROM java:8-jdk-alpine
VOLUME /tmp
EXPOSE 9012
ARG JAR_FILE=target/planing-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} planing.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/planing.jar"]