
package com.cg.osce.apibuilder.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "petstore_auth"
})
public class Security {

   
	

	@JsonProperty("petstore_auth")
    private List<String> petstoreAuth = null;

    @JsonProperty("petstore_auth")
    public List<String> getPetstoreAuth() {
        return petstoreAuth;
    }

    @JsonProperty("petstore_auth")
    public void setPetstoreAuth(List<String> petstoreAuth) {
        this.petstoreAuth = petstoreAuth;
    }

}
