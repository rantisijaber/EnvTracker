package com.jaberrantisi.lambdafunction.functions;

import com.jaberrantisi.lambdafunction.model.EnvironmentMessage;
import com.jaberrantisi.lambdafunction.service.DynamoDbService;
import com.jaberrantisi.lambdafunction.service.SnsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Component
public class LambdaFunctions {
    private final DynamoDbService dynamoDbService;
    private final SnsService snsService;

    public LambdaFunctions(DynamoDbService dynamoDbService, SnsService snsService) {
        this.dynamoDbService = dynamoDbService;
        this.snsService = snsService;
    }

    @Bean
    public Supplier<String> test() {
        return () -> {
            return "hello world";
        };
    }
    @Bean
    public Consumer<Map<String, Object>> lambdaFunction() {
        return messageDTO -> {
            EnvironmentMessage environmentMessage = EnvironmentMessage.builder()
                    .date(messageDTO.get("date").toString())
                    .co2Ppm(Integer.parseInt(messageDTO.get("co2Ppm").toString()))
                    .tempC(Double.parseDouble((messageDTO.get("tempC").toString())))
                    .humidityPercentage(Double.parseDouble(messageDTO.get("humidityPercentage").toString()))
                    .build();
            dynamoDbService.saveToDynamo(environmentMessage);
        };

    }
}
