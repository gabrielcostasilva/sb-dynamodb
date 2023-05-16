package com.example.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "customer")
public record Customer(String id, String fullname) {

    @DynamoDBHashKey 
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getFullname() {
        return fullname;
    }
}