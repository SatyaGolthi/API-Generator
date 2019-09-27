
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "xml",
    "items"
})
public class Tags {

public Tags() {
    	this.type="object";
    }
    @JsonProperty("type")
    private String type;
    
    
    @JsonProperty("xml")
    private Xml_ xml;
    @JsonProperty("items")
    private Items_ items;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("xml")
    public Xml_ getXml() {
        return xml;
    }

    @JsonProperty("xml")
    public void setXml(Xml_ xml) {
        this.xml = xml;
    }

    @JsonProperty("items")
    public Items_ getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items_ items) {
        this.items = items;
    }

}
