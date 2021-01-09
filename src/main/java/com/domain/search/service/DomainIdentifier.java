package com.domain.search.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.search.godaddy.api.GodaddyApiServiceCall;
import com.domain.search.model.GetDomainStatusResponse;
import com.domain.search.model.RetryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
public class DomainIdentifier {

	Logger logger = LoggerFactory.getLogger(DomainIdentifier.class);

	private ObjectMapper objectMapper = new ObjectMapper();
	private Gson gson = new Gson();
	
	@Autowired
	private GodaddyApiServiceCall apiService;

	@GetMapping("/getDomains")
	public String getDomains() throws IOException, InterruptedException {
		// available domains map
		Map<String, String> availableDomainsMap = new HashMap<>();
		// available count map
		int totalCount = 0;
		int availableCount = 0;
		
		try {
			defaultTwoCharSearchLogic(availableDomainsMap, objectMapper, gson, totalCount,
					availableCount);
			
			return objectMapper.writeValueAsString(availableDomainsMap);

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return objectMapper.writeValueAsString(availableDomainsMap);
	}

	private Map<String, String> defaultTwoCharSearchLogic(Map<String, String> availableDomainsMap, ObjectMapper objectMapper,
			Gson gson, int totalCount, int availableCount) throws InterruptedException, JsonProcessingException {

		// generates two characters string
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
			for (char alphabet1 = 'A'; alphabet1 <= 'Z'; alphabet1++) {
				String domain = new StringBuilder().append(alphabet).append(alphabet1).toString() + ".com";

				GetDomainStatusResponse goDaddyResponse = null;
				try {
					goDaddyResponse = apiService.getSingleDomainStatus(domain);
				} catch (Exception ex) {
					// fetching error message from go daddy
					String message = ex.getMessage();
					// Cut off the string to get JSON string
					String errorResponsejson = message.substring(message.indexOf(':') + 1);
					// converting it to array to fetch the first value
					RetryResponse[] fromJson = gson.fromJson(errorResponsejson, RetryResponse[].class);
					// fetching first value to get retry after seconds
					RetryResponse convertedObject = gson.fromJson(gson.toJson(fromJson[0]), RetryResponse.class);
					// calculating the time to wait before next call
					Integer timeToWait = convertedObject.getRetryAfterSec() != null ? convertedObject.getRetryAfterSec()
							: 30;
					// sleep as go daddy has 60 calls per minutes restriction
					Thread.sleep(timeToWait * 1000);
					// next call
					goDaddyResponse = apiService.getSingleDomainStatus(domain);
				}

				// get available domains and count
				getAvailableDomains(availableDomainsMap, availableCount, goDaddyResponse);

				totalCount++;
			}
		}
		availableDomainsMap.put("totalCount", String.valueOf(totalCount));
		availableDomainsMap.put("availableCount", String.valueOf(availableCount));
		logger.info("totalCount: " + totalCount + ", availableCount: " + availableCount);
		return availableDomainsMap;
	}

	private void getAvailableDomains(Map<String, String> availableDomainsMap, int availableCount,
			GetDomainStatusResponse goDaddyResponse) {
		if (null != goDaddyResponse && goDaddyResponse.getAvailable()) {
			availableDomainsMap.put(String.valueOf(availableCount), goDaddyResponse.getDomain());
			availableCount++;
		}
	}
}
