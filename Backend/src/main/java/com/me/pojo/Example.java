/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.pojo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"status",
"totalResults",
"articles"
})
public class Example {

	@JsonProperty("status")
	private String status;

	@JsonProperty("totalResults")
	private Integer totalResults;
	
	@JsonProperty("articles")
	private Articles articles;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public Articles getArticles() {
		return articles;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	
}
