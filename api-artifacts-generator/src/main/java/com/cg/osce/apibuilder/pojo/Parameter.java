
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "in", "description", "required", "type", "items", "collectionFormat", "schema", "format" })
public class Parameter {

	@JsonProperty("name")
	private String name;
	@JsonProperty("in")
	private String in;
	@JsonProperty("description")
	private String description;
	@JsonProperty("required")
	private Boolean required;
	@JsonProperty("type")
	private String type;
	@JsonProperty("items")
	private Items items;
	@JsonProperty("collectionFormat")
	private String collectionFormat;
	@JsonProperty("schema")
	private Schema schema;
	@JsonProperty("format")
    private String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("in")
	public String getIn() {
		return in;
	}

	@JsonProperty("in")
	public void setIn(String in) {
		this.in = in;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("required")
	public Boolean getRequired() {
		return required;
	}

	@JsonProperty("required")
	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("items")
	public Items getItems() {
		return items;
	}

	@JsonProperty("items")
	public void setItems(Items items) {
		this.items = items;
	}

	@JsonProperty("collectionFormat")
	public String getCollectionFormat() {
		return collectionFormat;
	}

	@JsonProperty("collectionFormat")
	public void setCollectionFormat(String collectionFormat) {
		this.collectionFormat = collectionFormat;
	}

	@JsonProperty("schema")
	public Schema getSchema() {
		return schema;
	}

	@JsonProperty("schema")
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	

}
