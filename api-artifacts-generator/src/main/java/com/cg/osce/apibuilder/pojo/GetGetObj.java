
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "get"
})
public class GetGetObj {

    @JsonProperty("get")
    private Get get;

    @JsonProperty("get")
    public Get getGet() {
        return get;
    }

    @JsonProperty("get")
    public void setGet(Get get) {
        this.get = get;
    }

}
