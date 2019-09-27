
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "items",
    "$ref"
})
public class Schema {

    @JsonProperty("type")
    private String type;
    @JsonProperty("items")
    private Items_ items;
    @JsonProperty("$ref")
    private String $ref;
    

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("items")
    public Items_ getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items_ items) {
        this.items = items;
    }

	public String get$ref() {
		return $ref;
	}

	public void set$ref(String $ref) {
		this.$ref = $ref;
	}

   
		
	}

