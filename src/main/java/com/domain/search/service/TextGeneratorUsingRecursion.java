package com.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextGeneratorUsingRecursion {

	char[] charSet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	@GetMapping(path = "/getFromRecursion")
	public List<String> text(int domainLength, Integer limit) {
		List<String> generatedDomains = new ArrayList<>();
		limit = limit != null ? limit : 500;
		printkLengthsRec(charSet, "", charSet.length, domainLength, generatedDomains, limit);
		return generatedDomains;
	}

	public void printkLengthsRec(char[] set, String prefix, int charLength, int domainLength,
			List<String> generatedDomains, int limit) {
		if (domainLength == 0) {
			generatedDomains.add(prefix+".com");
			System.out.println(prefix);
			return;
		}
		for (int i = 0; i < charLength; i++) {
			if (generatedDomains.size() == limit)
				break;
			String newPrefix = prefix + set[i];
			printkLengthsRec(set, newPrefix, charLength, domainLength - 1, generatedDomains, limit);
		}
	}

}
