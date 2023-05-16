package com.example.dynamodb;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DynamodbApplicationTests {

	@Autowired
	private CustomerRepository repo;

	@Test
	public void test1() {
		var entity = repo.save(new Customer(UUID.randomUUID().toString(), "John Doe"));

		assertNotNull(entity);
	}

}
