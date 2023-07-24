# starts from an official Apache Maven image
FROM maven:3-eclipse-temurin-17

COPY src /tmp/src
COPY pom.xml /tmp
WORKDIR /tmp/

CMD ["mvn", "spring-boot:run"]
