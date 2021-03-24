package com.logistic.rms;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = RmsApplication.class)

class RmsApplicationTests {
	@Test
	public void whenSpringContextIsBootstrapped_thenNoExceptions() {
	}

}
