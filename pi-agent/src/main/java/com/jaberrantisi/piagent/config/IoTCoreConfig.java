package com.jaberrantisi.piagent.config;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public AWSIotMqttClient awsIotMqttClient() throws AWSIotException {
        return new AWSIotMqttClient(endpoint, clientId, iotCert, key);
    }

}
