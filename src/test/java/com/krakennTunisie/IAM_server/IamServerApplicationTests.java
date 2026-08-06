package com.krakennTunisie.IAM_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@ActiveProfiles("test")
class IamServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
