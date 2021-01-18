package com.domain.search.godaddy.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.domain.search.model.DomainStatusResponse;
import com.domain.search.model.DomainStatusResponseList;

@RestController
public class GodaddyApiServiceCall {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${prodauth}")
	private String auth;

	@GetMapping("/direct/single/domain")
	public DomainStatusResponse getSingleDomainStatus(String domain) {
		String url = "https://api.ote-godaddy.com/v1/domains/available?domain=" + domain;
		HttpEntity<DomainStatusResponse> entity = new HttpEntity<>(getHeaders());
		HttpEntity<DomainStatusResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				DomainStatusResponse.class);
		return response != null ? response.getBody() : null;
	}

	@PostMapping("/direct/post/domains")
	public ResponseEntity<DomainStatusResponseList> getMultiDomainAvailableStatus(@RequestBody String domains) {
		String url = "https://api.godaddy.com/v1/domains/available";
		HttpEntity<String> entity = new HttpEntity<>(domains, getHeaders());
		ResponseEntity<DomainStatusResponseList> postForEntity = restTemplate.postForEntity(url, entity, DomainStatusResponseList.class);
		return postForEntity;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", auth);
		return headers;
	}
}