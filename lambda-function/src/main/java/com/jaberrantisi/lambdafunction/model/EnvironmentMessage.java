package com.jaberrantisi.lambdafunction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@DynamoDbBean
public class EnvironmentMessage {

    private String date;
    private Integer co2Ppm;
    private Double tempC;
    private Double humidityPercentage;
    private Double pressure;


    @DynamoDbPartitionKey
    public String getDate() {
        return this.date;
    }
}
