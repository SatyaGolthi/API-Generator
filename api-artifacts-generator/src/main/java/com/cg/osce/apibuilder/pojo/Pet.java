
package com.cg.osce.apibuilder.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "required",
    "properties",
    "xml"
})
public class Pet {

    @JsonProperty("type")
    private String type;
    @JsonProperty("required")
    private List<String> required = null;
    @JsonProperty("properties")
    private Properties properties;
    @JsonProperty("xml")
    private Xml__ xml;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("required")
    public List<String> getRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(List<String> required) {
        this.required = required;
    }

    @JsonProperty("properties")
    public Properties getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @JsonProperty("xml")
    public Xml__ getXml() {
        return xml;
    }

    @JsonProperty("xml")
    public void setXml(Xml__ xml) {
        this.xml = xml;
    }

}
