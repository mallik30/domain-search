package com.domain.search.godaddy.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.domain.search.model.GetDomainStatusResponse;

@RestController
public class GodaddyApiServiceCall {

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/domains/available")
	public GetDomainStatusResponse getSingleDomainStatus(String domain) {
		String url = "https://api.ote-godaddy.com/v1/domains/available?domain=" + domain;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "sso-key 3mM44UbCEcaxKm_65wZtrcxxu5haEursuve5d:U3YqpBN5D3xvvGacirvD7c");
		HttpEntity<GetDomainStatusResponse> entity = new HttpEntity<>(headers);
		HttpEntity<GetDomainStatusResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				GetDomainStatusResponse.class);
		return response != null ? response.getBody() : null;
	}
}