# DYNAMODB WITH SPRING DATA
This project shows a DynamoDB community-suported implementation of Spring Data. [DynamoDB](https://aws.amazon.com/dynamodb/) is an [AWS](https://aws.amazon.com/) service providing a NoSQL, single-table, high performance database. [Spring Data](https://spring.io/projects/spring-data) is a [Spring](https://spring.io) project that provides an abstraction layer for managing databases in a standardised way. 

Spring Data officially supports several databases out-of-the-box, but DynamoDB is not one of them. However, members of the community can freely extend Spring Data for supporting other databases - like the [Spring Data DynamoDB](https://github.com/boostchicken/spring-data-dynamodb) project.

## Project Overview
This project consists of two main classes: `DynamoDBConfig.java` and `Customer.java`. 

The [`DynamoDBConfig.java`](./src/main/java/com/example/dynamodb/DynamoDBConfig.java) sets the DynamoDB availability region, and maps the interface responsible for managing data, as follows.

```java
@org.springframework.context.annotation.Configuration
@org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories(basePackageClasses = CustomerRepository.class)
public class DynamoDBConfig {

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.US_EAST_1).build();
	}
}
```

The [`Customer.java`](./src/main/java/com/example/dynamodb/Customer.java) _record_ maps Java and DynamoDB attributes, as presented below.

```java
@com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable(tableName = "customer")
public record Customer(String id, String fullname) {

    @com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey 
    public String getId() {
        return id;
    }

    @com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
    public String getFullname() {
        return fullname;
    }
}
```

> The attribute mapping annotation requires _gets_.

As usual, [an extension](./src/main/java/com/example/dynamodb/CustomerRepository.java) of a `CrudRepository` interface maps both `Customer` and its HashKey(primary key) for easily managing data.

There is a [simple test](./src/test/java/com/example/dynamodb/DynamodbApplicationTests.java) that saves an item to the database.

> This project accesses AWS resources. Therefore,  you need your [permissions properly configured](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/SettingUp.DynamoWebService.html). In this project, our permissions are set using [AWS CLI](https://aws.amazon.com/cli/).

## Creating Infrastructure
This project provides a template file ([cf-dynamodb-template.yaml](./cf-dynamodb-template.yaml)) for easily provisioning the infrastructure with AWS CloudFormation. [AWS CloudFormation](https://aws.amazon.com/cloudformation/) is the AWS infrastructure as service (IaC) main service.  

## Dependencies
This project uses `io.github.boostchicken.spring-data-dynamodb:5.2.5` dependency, which provides the Spring Data DynamoDB library.

## Project Setup
You need Java 17 (at least) and [Maven](https://maven.apache.org/download.cgi) installed to run this project. In addition, you need an AWS account and the AWS CLI properly configured.

1. From your terminal, create your AWS resources using the AWS CLI
```
aws cloudformation create-stack \
  --stack-name dynamodb-customer-table \
  --template-body file:///cf-dynamodb-template.yaml
```

2. Run the unit test: `mvn test`.

3. Check the item using the DynamoDB console or the AWS CLI, as follows.

```
aws dynamodb scan \
    --table-name dynamodb-customer-table
```

