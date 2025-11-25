# Use 64-bit ARM Debian to match Pi architecture
FROM --platform=linux/arm64 debian:bookworm-slim

WORKDIR /app

RUN apt-get update && apt-get install -y \
    openjdk-17-jre \
    && rm -rf /var/lib/apt/lists/*

COPY target/pi-agent-0.0.1-SNAPSHOT.jar ./pi-agent.jar

VOLUME /app/certs

ENV AWS_IOT_CLIENT_ENDPOINT=""
ENV AWS_IOT_CLIENT_ID=""
ENV AWS_IOT_CERT=""
ENV AWS_IOT_KEY=""
ENV AWS_IOT_CA=""
ENV BME280_PATH=""

EXPOSE 8080

ENTRYPOINT ["java","-jar","pi-agent.jar"]

