package com.domain.search.z.ignore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class TextGenerator {

	private Gson gson = new Gson();

	@GetMapping("/get5Char")
	public String get5Char(Integer limit) {
		List<String> list = new ArrayList<>();
		int totalCount = 0;
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
			if (null != limit && totalCount == limit) {
				break;
			}
			for (char alphabet1 = 'A'; alphabet1 <= 'Z'; alphabet1++) {
				if (null != limit && totalCount == limit) {
					break;
				}
				for (char alphabet2 = 'A'; alphabet2 <= 'Z'; alphabet2++) {
					if (null != limit && totalCount == limit) {
						break;
					}
					for (char alphabet3 = 'A'; alphabet3 <= 'Z'; alphabet3++) {
						if (null != limit && totalCount == limit) {
							break;
						}
						for (char alphabet4 = 'A'; alphabet4 <= 'Z'; alphabet4++) {
							if (null != limit && totalCount == limit) {
								break;
							}
							totalCount++;
							String domain = new StringBuilder().append(alphabet).append(alphabet1).append(alphabet2)
									.append(alphabet3).append(alphabet4).toString() + ".com";
							list.add(domain);
						}
					}
				}
			}
		}
		return gson.toJson(list);
	}

}
