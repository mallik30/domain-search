package com.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.domain.search.godaddy.api.GodaddyApiServiceCall;
import com.domain.search.model.DomainStatusResponse;
import com.domain.search.model.DomainStatusResponseList;
import com.domain.search.model.RetryResponse;
import com.google.gson.Gson;

@RestController
public class GenerateDomainNames {

	@Autowired
	private GodaddyApiServiceCall godaddyApiServiceCall;

	private Gson gson = new Gson();

	private char[] charSet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	@GetMapping(path = "/generate")
	public Object callBulkGoDaddyApi(int domainLength) throws InterruptedException {
		List<String> generatedDomains = new ArrayList<>();

		List<DomainStatusResponse> availableResponse = new ArrayList<>();
		List<DomainStatusResponse> allResponses = new ArrayList<>();
		List<String> availableDomainsOnly = new ArrayList<>();
		// generate domains
		identifyDomains(charSet, "", charSet.length, domainLength, generatedDomains, 500, availableResponse,
				availableDomainsOnly, allResponses);
		if (!availableDomainsOnly.isEmpty()) {
			return availableDomainsOnly;
		}
		if (!availableResponse.isEmpty()) {
			return availableResponse;
		}

		if (allResponses.isEmpty() && generatedDomains.size() < 500) {
			return godaddyApiServiceCall.getMultiDomainAvailableStatusTest(gson.toJson(generatedDomains));
		}
		return allResponses;
	}

	public void identifyDomains(char[] charSet, String prefix, int charLength, int domainLength,
			List<String> generatedDomains, int domainLimit, List<DomainStatusResponse> availableResponse,
			List<String> availableDomainsOnly, List<DomainStatusResponse> allResponse) throws InterruptedException {

		if (domainLength == 0) {
			generatedDomains.add(prefix + ".com");
			return;
		}
		for (int i = 0; i < charLength; i++) {
			if (generatedDomains.size() == domainLimit) {
				ResponseEntity<String> multiDomainAvailableStatus = null;

				try {
					multiDomainAvailableStatus = godaddyApiServiceCall
							.getMultiDomainAvailableStatusTest(gson.toJson(generatedDomains));
				} catch (HttpStatusCodeException ex) {
					// fetching error message from go daddy
					System.out.println("Message:"+ex.getResponseBodyAsString());
					RetryResponse convertedObject = gson.fromJson(ex.getResponseBodyAsString(), RetryResponse.class);
					System.out.println("convertedObject"+convertedObject);
					// calculating the time to wait before next call
					Integer timeToWait = convertedObject.getRetryAfterSec() != null ? convertedObject.getRetryAfterSec()
							: 30;
					System.out.println("Time to wait in sec:" + timeToWait);
					// sleep as go daddy has 60 calls per minutes restriction
					Thread.sleep(timeToWait * 1000);
					// next call
					multiDomainAvailableStatus = godaddyApiServiceCall
							.getMultiDomainAvailableStatusTest(gson.toJson(generatedDomains));
				}

				for (DomainStatusResponse dsp : gson
						.fromJson(multiDomainAvailableStatus.getBody(), DomainStatusResponseList.class).getDomains()) {
					if (dsp.getAvailable()) {
						availableResponse.add(dsp);
						availableDomainsOnly.add(dsp.getDomain());
					}
					allResponse.add(dsp);
				}
				System.out.println("Domains" + availableDomainsOnly);
				// clear for fresh records
				generatedDomains.clear();
			}
			String newPrefix = prefix + charSet[i];
			identifyDomains(charSet, newPrefix, charLength, domainLength - 1, generatedDomains, domainLimit,
					availableResponse, availableDomainsOnly, allResponse);
		}
	}

}
