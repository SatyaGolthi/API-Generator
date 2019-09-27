package com.cg.osce.apibuilder.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String apilocation = "src/main/resources/apiDocument/";

	private String xsdlocation = "/xsd/";

	public String getApilocation() {
		return apilocation;
	}

	public void setApilocation(String apilocation) {
		this.apilocation = apilocation;
	}

	public String getXsdlocation() {
		return xsdlocation;
	}

	public void setXsdlocation(String xsdlocation) {
		this.xsdlocation = xsdlocation;
	}

}
