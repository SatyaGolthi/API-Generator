package com.cg.osce.apibuilder.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.osce.apibuilder.FormWrapper;
import com.cg.osce.apibuilder.exception.APIBuilderException;

public interface IYamlGeneratorService {

	void handleFileUpload(FormWrapper formWrapper, RedirectAttributes redirectAttributes) throws APIBuilderException;

}
