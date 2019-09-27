
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
public class PhotoUrls {

    @JsonProperty("type")
    private String type;
    @JsonProperty("xml")
    private Xml_ xml;
    @JsonProperty("items")
    private Items items;

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
    public Items getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items items) {
        this.items = items;
    }

}
