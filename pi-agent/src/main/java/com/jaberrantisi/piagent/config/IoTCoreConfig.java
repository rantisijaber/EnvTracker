package com.jaberrantisi.piagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder;

@Configuration
public class IoTCoreConfig {
    @Value("${AWS_IOT_CLIENT_ENDPOINT}")
    public String endpoint;

    @Value("${AWS_IOT_CLIENT_ID}")
    public String clientId;

    @Value("${AWS_IOT_CERT}")
    public String iotCert;

    @Value("${AWS_IOT_KEY}")
    public String key;



    @Bean
    public AwsIotMqttConnectionBuilder awsIotMqttClient() throws Exception {

        return AwsIotMqttConnectionBuilder
               .newMtlsBuilderFromPath(iotCert, key)
                    .withEndpoint(endpoint)
                    .withClientId(clientId)
                    .withCleanSession(true);


    }

}
