package com.domain.search.model;

import java.util.ArrayList;
import java.util.List;

public class DomainStatusResponseList {

	private List<DomainStatusResponse> domains = new ArrayList<>();

	public List<DomainStatusResponse> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainStatusResponse> domains) {
		this.domains = domains;
	}

	@Override
	public String toString() {
		return "DomainStatusResponseList [domains=" + domains + "]";
	}

}