
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "wrapped"
})
public class Xml_ {

    @JsonProperty("name")
    private String name;
    @JsonProperty("wrapped")
    private Boolean wrapped;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("wrapped")
    public Boolean getWrapped() {
        return wrapped;
    }

    @JsonProperty("wrapped")
    public void setWrapped(Boolean wrapped) {
        this.wrapped = wrapped;
    }

}
