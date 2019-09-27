
package com.cg.osce.apibuilder.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "200",
    "400",
    "404",
    "405"
})
public class Responses {

    @JsonProperty("200")
    private com.cg.osce.apibuilder.pojo.Response200 response200;
    @JsonProperty("400")
    private com.cg.osce.apibuilder.pojo.Response400 response400;
    @JsonProperty("404")
    private com.cg.osce.apibuilder.pojo.Response404 response404;
    @JsonProperty("405")
    private com.cg.osce.apibuilder.pojo.Response405 response405;

    public com.cg.osce.apibuilder.pojo.Response200 getResponse200() {
		return response200;
	}

	public void setResponse200(com.cg.osce.apibuilder.pojo.Response200 response200) {
		this.response200 = response200;
	}

	public com.cg.osce.apibuilder.pojo.Response400 getResponse400() {
		return response400;
	}

	public void setResponse400(com.cg.osce.apibuilder.pojo.Response400 response400) {
		this.response400 = response400;
	}

	public com.cg.osce.apibuilder.pojo.Response404 getResponse404() {
		return response404;
	}

	public void setResponse404(com.cg.osce.apibuilder.pojo.Response404 response404) {
		this.response404 = response404;
	}

	public com.cg.osce.apibuilder.pojo.Response405 getResponse405() {
		return response405;
	}

	public void setResponse405(com.cg.osce.apibuilder.pojo.Response405 response405) {
		this.response405 = response405;
	}

	@JsonProperty("200")
    public com.cg.osce.apibuilder.pojo.Response200 get200() {
        return response200;
    }

    @JsonProperty("200")
    public void set200(com.cg.osce.apibuilder.pojo.Response200 response200) {
        this.response200 = response200;
    }

    @JsonProperty("400")
    public com.cg.osce.apibuilder.pojo.Response400 get400() {
        return response400;
    }

    @JsonProperty("400")
    public void set400(com.cg.osce.apibuilder.pojo.Response400 response400) {
        this.response400 = response400;
    }

}
