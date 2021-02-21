package com.domain.search.z.ignore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.search.godaddy.api.GodaddyApiServiceCall;
import com.domain.search.model.DomainStatusResponse;
import com.domain.search.model.DomainStatusResponseList;
import com.domain.search.model.RetryResponse;
import com.google.gson.Gson;

@RestController
public class TextGeneratorUsingRecursion {

	@Autowired
	private GodaddyApiServiceCall godaddyApiServiceCall;

	private Gson gson = new Gson();

	private char[] charSet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	@GetMapping(path = "/getFromRecursion")
	public List<String> generateText(int domainLength, Integer domainLimit) {
		List<String> generatedDomains = new ArrayList<>();
		domainLimit = domainLimit != null ? domainLimit : 500;
		// generate domains
		printkLengthsRec(charSet, "", charSet.length, domainLength, generatedDomains, domainLimit);

		return generatedDomains;
	}

	public void printkLengthsRec(char[] charSet, String prefix, int charLength, int domainLength,
			List<String> generatedDomains, int domainLimit) {

		if (domainLength == 0) {
			generatedDomains.add(prefix);
//			System.out.println(prefix);
			return;
		}
		for (int i = 0; i < charLength; i++) {
			if (generatedDomains.size() == domainLimit)
				break;
			String newPrefix = prefix + charSet[i];
			printkLengthsRec(charSet, newPrefix, charLength, domainLength - 1, generatedDomains, domainLimit);
		}
	}

	@GetMapping(path = "/generateAndSearch")
	public List<DomainStatusResponse> callBulkGoDaddyApi(int domainLength) throws InterruptedException {
		List<String> generatedDomains = new ArrayList<>();

		List<DomainStatusResponse> availableDomains = new ArrayList<>();
		List<String> availableDomainsOnly = new ArrayList<>();
		// generate domains
		identifyDomains(charSet, "", charSet.length, domainLength, generatedDomains, 500, availableDomains,
				availableDomainsOnly);
		return availableDomains;
	}

	public void identifyDomains(char[] charSet, String prefix, int charLength, int domainLength,
			List<String> generatedDomains, int domainLimit, List<DomainStatusResponse> availableDomains,
			List<String> availableDomainsOnly) throws InterruptedException {

		if (domainLength == 0) {
			generatedDomains.add(prefix + ".com");
			return;
		}
		for (int i = 0; i < charLength; i++) {
			if (generatedDomains.size() == domainLimit) {
				ResponseEntity<DomainStatusResponseList> multiDomainAvailableStatus = null;

				try {
					multiDomainAvailableStatus = godaddyApiServiceCall
							.getMultiDomainAvailableStatus(gson.toJson(generatedDomains));
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
					System.out.println("Time to wait in sec:" + timeToWait);
					// sleep as go daddy has 60 calls per minutes restriction
					Thread.sleep(timeToWait * 1000);
					// next call
					multiDomainAvailableStatus = godaddyApiServiceCall
							.getMultiDomainAvailableStatus(gson.toJson(generatedDomains));
				}

				for (DomainStatusResponse dsp : multiDomainAvailableStatus.getBody().getDomains()) {
					if (dsp.getAvailable()) {
						availableDomains.add(dsp);
						availableDomainsOnly.add(dsp.getDomain());
					}
				}
				System.out.println("Domains" + availableDomainsOnly);
				// clear for fresh records
				generatedDomains.clear();
			}
			String newPrefix = prefix + charSet[i];
			identifyDomains(charSet, newPrefix, charLength, domainLength - 1, generatedDomains, domainLimit,
					availableDomains, availableDomainsOnly);
		}
	}

}
