package com.jaberrantisi.lambdafunction.service;

import com.jaberrantisi.lambdafunction.model.EnvironmentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsService {
    private final SnsClient snsClient;
    private String arn;

    @Autowired
    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }


    public void sendEmailMessage(String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .subject("Home Air Quality Alert")
                .topicArn(arn)
                .build();

        snsClient.publish(request);
    }
    public String emailTemplate(EnvironmentMessage envMessage) {
        return "Here are the air quality statistics: \n\n\n" +
                "Carbon Dioxide: " + envMessage.getCo2Ppm() + "PPM\n" +
                "Temperature: " + envMessage.getTempC() + "°C\n" +
                "Humidity: " + envMessage.getHumidityPercentage() + "%";

    }



}
