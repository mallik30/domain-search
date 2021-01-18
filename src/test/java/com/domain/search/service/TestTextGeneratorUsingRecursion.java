package com.domain.search.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TestTextGeneratorUsingRecursion {

	@Test
	public void testOneCharRecursion() {
		TextGeneratorUsingRecursion generatorUsingRecursion = new TextGeneratorUsingRecursion();
		List<String> text = generatorUsingRecursion.generateText(1,26);
		int size = text.size();
		assertEquals(26, size);
	}

	@Test
	public void testTwoCharRecursion() {
		TextGeneratorUsingRecursion generatorUsingRecursion = new TextGeneratorUsingRecursion();
		List<String> text = generatorUsingRecursion.generateText(2,676);
		int size = text.size();
		assertEquals(676, size);
	}

	@Test
	public void testThreeCharRecursion() {
		TextGeneratorUsingRecursion generatorUsingRecursion = new TextGeneratorUsingRecursion();
		List<String> text = generatorUsingRecursion.generateText(3,17576);
		int size = text.size();
		assertEquals(17576, size);
	}
	
	@Test
	public void testFourCharRecursion() {
		TextGeneratorUsingRecursion generatorUsingRecursion = new TextGeneratorUsingRecursion();
		List<String> text = generatorUsingRecursion.generateText(4,456976);
		int size = text.size();
		assertEquals(456976, size);
	}
	
	@Test
	public void testFiveCharRecursion() {
		TextGeneratorUsingRecursion generatorUsingRecursion = new TextGeneratorUsingRecursion();
		List<String> text = generatorUsingRecursion.generateText(5,11881376);
		int size = text.size();
		assertEquals(11881376, size);
	}

}
