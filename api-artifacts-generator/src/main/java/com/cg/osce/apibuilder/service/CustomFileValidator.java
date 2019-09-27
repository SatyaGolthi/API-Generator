package com.cg.osce.apibuilder.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.cg.osce.apibuilder.FormWrapper;

@Component
public class CustomFileValidator implements Validator {
	public static final String PNG_MIME_TYPE = ".xsd";
	public static final long ONE_MB_IN_BYTES = 1048576;

	@Override
	public boolean supports(Class<?> clazz) {
		return FormWrapper.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormWrapper formWrapper = (FormWrapper) target;
		MultipartFile file = formWrapper.getFile();
		if (file.isEmpty()) {
			errors.rejectValue("file", "upload.file.required");
		} else if (!(file.getOriginalFilename().endsWith(".xsd") || file.getOriginalFilename().endsWith(".json"))) {
			errors.rejectValue("file", "upload.invalid.file.type");
		} else if (file.getSize() > ONE_MB_IN_BYTES) {
			errors.rejectValue("file", "upload.exceeded.file.size");
		}

	}

}