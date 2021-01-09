package com.domain.search.model;

public class GetDomainStatusResponse {

	private Boolean available;
	private String currency;
	private Boolean definitive;
	private String domain;
	private Integer period;
	private Integer price;

	public Boolean getAvailable() {
		return available;
	}

	public String getCurrency() {
		return currency;
	}

	public Boolean getDefinitive() {
		return definitive;
	}

	public String getDomain() {
		return domain;
	}

	public Integer getPeriod() {
		return period;
	}

	public Integer getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "GetDomainStatusResponse [available=" + available + ", currency=" + currency + ", definitive="
				+ definitive + ", domain=" + domain + ", period=" + period + ", price=" + price + "]";
	}

}
