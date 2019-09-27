
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "description", "schema" })
public class Response200 {

	@JsonProperty("description")
	private String description;
	@JsonProperty("schema")
	private ObjectNode schema;

	public Response200() {
		this.description = "successful operation";
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	public ObjectNode getSchema() {
		return schema;
	}

	public void setSchema(ObjectNode schema) {
		this.schema = schema;
	}
}
