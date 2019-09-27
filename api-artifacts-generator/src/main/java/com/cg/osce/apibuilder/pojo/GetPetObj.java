
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Pet"
})
public class GetPetObj {

    @JsonProperty("Pet")
    private Pet pet;

    @JsonProperty("Pet")
    public Pet getPet() {
        return pet;
    }

    @JsonProperty("Pet")
    public void setPet(Pet pet) {
        this.pet = pet;
    }

}
