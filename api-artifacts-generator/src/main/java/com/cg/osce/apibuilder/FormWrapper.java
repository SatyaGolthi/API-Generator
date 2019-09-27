package com.cg.osce.apibuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class FormWrapper {

	@NotNull
	private MultipartFile file;
	
	private String swaggerVersion = "2.0";
	@NotNull
	@NotEmpty
	private String title;
	
	@NotEmpty
	@Pattern(regexp ="[a-zA-Z0-9/.:,#@$!~() ]{5,100}$",message="Minimum Length of the description is 5")
	private String description;
	
	@NotEmpty
	@Pattern(regexp ="[a-z.]{1,70}$",message="Provide a valid Host, Ex:google.com")
	private String host;
	
	@NotEmpty
	@Pattern(regexp ="^/[a-zA-Z0-9/]*$",message="Base path should start with \"/\"")
	private String basepath;

	public String getSwaggerVersion() {
		return swaggerVersion;
	}

	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}

}