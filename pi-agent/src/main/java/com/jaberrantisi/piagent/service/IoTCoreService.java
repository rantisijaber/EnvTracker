package com.jaberrantisi.piagent.service;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IoTCoreService {
    private final AWSIotMqttClient IotMqttClient;

    @Autowired
    public IoTCoreService(AWSIotMqttClient IotMqttClient) {
        this.IotMqttClient = IotMqttClient;
    }

    public void sendMessage(String message) {
        try {
            System.out.println("SendMessage IotCoreService");
            IotMqttClient.connect();

            IotMqttClient.publish("sensors/data", AWSIotQos.QOS1, message);

            IotMqttClient.disconnect();

        } catch (AWSIotException e) {
            throw new RuntimeException("Could not connect to IOT Core: " + e);
        }
    }
}
