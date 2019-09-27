
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "delete"
})
public class GetDeleteObj {

    @JsonProperty("delete")
    private Delete delete;

    @JsonProperty("delete")
    public Delete getDelete() {
        return delete;
    }

    @JsonProperty("delete")
    public void setDelete(Delete delete) {
        this.delete = delete;
    }

}
