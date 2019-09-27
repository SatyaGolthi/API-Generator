
package com.cg.osce.apibuilder.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tags", "summary", "description", "operationId", "consumes", "produces", "parameters", "responses", "security" })
public class Post {


	@JsonProperty("tags")
	private List<String> tags = null;
	@JsonProperty("summary")
	private String summary;
	@JsonProperty("description")
    private String description;
	

	@JsonProperty("operationId")
	private String operationId;
	@JsonProperty("consumes")
	private List<String> consumes = null;
	@JsonProperty("produces")
	private List<String> produces = null;
	@JsonProperty("parameters")
	private List<Parameter> parameters = null;
	@JsonProperty("responses")
	private Responses responses;
	@JsonProperty("security")
	private List<Security> security = null;

	@JsonProperty("tags")
	public List<String> getTags() {
		return tags;
	}

	@JsonProperty("tags")
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@JsonProperty("summary")
	public String getSummary() {
		return summary;
	}

	@JsonProperty("summary")
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonProperty("operationId")
	public String getOperationId() {
		return operationId;
	}

	@JsonProperty("operationId")
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@JsonProperty("consumes")
	public List<String> getConsumes() {
		return consumes;
	}

	@JsonProperty("consumes")
	public void setConsumes(List<String> consumes) {
		this.consumes = consumes;
	}

	@JsonProperty("produces")
	public List<String> getProduces() {
		return produces;
	}

	@JsonProperty("produces")
	public void setProduces(List<String> produces) {
		this.produces = produces;
	}

	@JsonProperty("parameters")
	public List<Parameter> getParameters() {
		return parameters;
	}

	@JsonProperty("parameters")
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@JsonProperty("responses")
	public Responses getResponses() {
		return responses;
	}

	@JsonProperty("responses")
	public void setResponses(Responses responses) {
		this.responses = responses;
	}

	@JsonProperty("security")
	public List<Security> getSecurity() {
		return security;
	}

	@JsonProperty("security")
	public void setSecurity(List<Security> security) {
		this.security = security;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
