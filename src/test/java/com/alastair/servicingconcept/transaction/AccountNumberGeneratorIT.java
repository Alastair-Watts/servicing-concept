package com.alastair.servicingconcept.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountNumberGeneratorIT {

	@Autowired
	private AccountNumberGenerator accountNumberGenerator;

	@Test
	public void test() {
		assertEquals(0, accountNumberGenerator.getNext().intValue());
		assertEquals(1, accountNumberGenerator.getNext().intValue());
		assertEquals(2, accountNumberGenerator.getNext().intValue());
		assertEquals(3, accountNumberGenerator.getNext().intValue());
		assertEquals(4, accountNumberGenerator.getNext().intValue());
	}
}
