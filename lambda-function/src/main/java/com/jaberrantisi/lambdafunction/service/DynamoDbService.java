package com.jaberrantisi.lambdafunction.service;

import com.jaberrantisi.lambdafunction.model.EnvironmentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class DynamoDbService {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    public DynamoDbService(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    public void saveToDynamo(EnvironmentMessage message) {
        DynamoDbTable<EnvironmentMessage> envMessageTable =
                dynamoDbEnhancedClient.table("env-messages", TableSchema.fromBean(EnvironmentMessage.class));
        envMessageTable.putItem(message);
    }
}
