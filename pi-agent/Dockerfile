FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/pi-agent-0.0.1-SNAPSHOT.jar ./pi-agent.jar

VOLUME /app/certs

ENV AWS_IOT_CLIENT_ENDPOINT=""
ENV AWS_IOT_CLIENT_ID=""
ENV AWS_IOT_CERT=""
ENV AWS_IOT_KEY=""
ENV AWS_IOT_CA=""

EXPOSE 8080

ENTRYPOINT ["java","-jar","pi-agent.jar"]

