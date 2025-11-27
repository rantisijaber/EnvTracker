package com.jaberrantisi.lambdafunction.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaberrantisi.lambdafunction.model.EnvironmentMessage;
import com.jaberrantisi.lambdafunction.service.DynamoDbService;
import com.jaberrantisi.lambdafunction.service.S3Service;
import com.jaberrantisi.lambdafunction.service.SnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Component
public class LambdaFunctions {
    private final DynamoDbService dynamoDbService;
    private final SnsService snsService;
    private final ObjectMapper objectMapper;
    private final S3Service s3Service;

    @Value("${BUCKET_NAME}")
    String bucketName;

    @Autowired
    public LambdaFunctions(DynamoDbService dynamoDbService,
                           SnsService snsService,
                           ObjectMapper objectMapper,
                           S3Service s3Service) {
        this.dynamoDbService = dynamoDbService;
        this.snsService = snsService;
        this.objectMapper = objectMapper;
        this.s3Service = s3Service;
    }

    @Bean
    public Consumer<Map<String, Object>> sendEmail() {
        return messageDTO -> {
            double temp = Double.parseDouble(messageDTO.get("temperature").toString());
            double humidity = Double.parseDouble(messageDTO.get("humidityPercentage").toString());
            double pressure = Double.parseDouble(messageDTO.get("pressure").toString());
            if (temp < 15 || temp > 30 || humidity < 25 || humidity > 60 || pressure < 985 || pressure > 1040) {
                EnvironmentMessage message = EnvironmentMessage.builder()
                        .tempC(temp)
                        .humidityPercentage(humidity)
                        .pressure(pressure)
                        .build();
                String template = snsService.emailTemplate(message);
                snsService.sendEmailMessage(template);
            }
        };
    }

    @Bean
    public Consumer<Map<String, Object>> saveToDynamo() {
        return messageDTO -> {
            EnvironmentMessage environmentMessage = EnvironmentMessage.builder()
                    .date(messageDTO.get("date").toString())
                    .co2Ppm(Integer.parseInt(messageDTO.get("co2Ppm").toString()))
                    .tempC(Double.parseDouble((messageDTO.get("tempC").toString())))
                    .humidityPercentage(Double.parseDouble(messageDTO.get("humidityPercentage").toString()))
                    .pressure(Double.parseDouble(messageDTO.get("pressure").toString()))
                    .build();
            dynamoDbService.saveToDynamo(environmentMessage);
        };
    }

    @Bean
    public Consumer<Map<String, Object>> saveToS3() {
        return messageDTO -> {
            try {
                String json = objectMapper.writeValueAsString(messageDTO);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
                String key = (String) messageDTO.get("date") + "/" + timestamp + ".json";

                s3Service.saveToS3(json, key, bucketName);

            } catch (JsonProcessingException e) {
                throw new RuntimeException("Could not Serialize object to json: " + e);
            }
        };
    }
}
