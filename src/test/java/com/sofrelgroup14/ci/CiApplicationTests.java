package com.sofrelgroup14.ci;

import static org.assertj.core.api.Assertions.assertThat;

import com.sofrelgroup14.ci.controller.BuildController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CiApplicationTests {

	@Autowired
	private BuildController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull(); // Source: https://spring.io/guides/gs/testing-web/
		// This line is a placeholder
		assertThat(true).isTrue();
	}
}
