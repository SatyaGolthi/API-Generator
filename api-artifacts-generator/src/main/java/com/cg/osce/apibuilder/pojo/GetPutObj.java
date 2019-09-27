
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "put"
})
public class GetPutObj {

    @JsonProperty("put")
    private Put put;

    @JsonProperty("put")
    public Put getPut() {
        return put;
    }

    @JsonProperty("put")
    public void setPut(Put put) {
        this.put = put;
    }

}
