package com.jaberrantisi.piagent.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.crt.mqtt.MqttMessage;
import software.amazon.awssdk.crt.mqtt.QualityOfService;
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder;

@Service
public class IoTCoreService {
    private final AwsIotMqttConnectionBuilder builder;

    @Autowired
    public IoTCoreService(AwsIotMqttConnectionBuilder builder) {
        this.builder = builder;
    }


    public void sendMessage(String message) {
        System.out.println("Trying to now connect");
        try (MqttClientConnection connection = builder.build()) {
            connection.connect().join();
            connection.publish(new MqttMessage
                    ("sensors/data", message.getBytes(), QualityOfService.AT_LEAST_ONCE));
            System.out.println("published");
            connection.disconnect().join();
        } catch (Exception e) {
            throw new RuntimeException("Exception: "  + e);
        }
    }
}
