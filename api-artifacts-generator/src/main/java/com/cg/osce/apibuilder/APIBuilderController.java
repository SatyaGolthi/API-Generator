package com.cg.osce.apibuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.osce.apibuilder.exception.APIBuilderException;
import com.cg.osce.apibuilder.service.CustomFileValidator;
import com.cg.osce.apibuilder.service.IYamlGeneratorService;
import com.cg.osce.apibuilder.storage.StorageFileNotFoundException;
import com.cg.osce.apibuilder.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

@CrossOrigin
@Controller
public class APIBuilderController {

	private final StorageService storageService;

	@Autowired
	CustomFileValidator customFileValidator;
	
	private String indexPage = "index";
	
	private String error = "error";

	@Autowired
	IYamlGeneratorService ixmlSchema2yaml;

	private static final Logger LOGGER = LoggerFactory.getLogger(APIBuilderController.class);

	private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));

	@Autowired
	public APIBuilderController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/asd")
	public String listUploadedFiles(Model model) {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(APIBuilderController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return indexPage;
	}

	@GetMapping("/")
	public String getAPIDocument(Model model) {

		FormWrapper wrapper = new FormWrapper();
		model.addAttribute("formWrapper", wrapper);
		Path path = storageService.load("API_Document.yaml");
		model.addAttribute("file",
				MvcUriComponentsBuilder
						.fromMethodName(APIBuilderController.class, "serveFile", path.getFileName().toString()).build()
						.toString());

		return indexPage;
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@CrossOrigin
	@PostMapping(value = "/")
	public String handleFileUpload(@ModelAttribute @Valid FormWrapper formWrapper, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		// file handling to upload it in the server path
		customFileValidator.validate(formWrapper, bindingResult);
		if (bindingResult.hasErrors()) {
			LOGGER.debug("BINDING RESULT ERROR");
			redirectAttributes.addFlashAttribute(error, "Valdiation's failed");
			model.addAttribute(error, "Valdiation's failed");
			return indexPage;
		}

		try {
			ixmlSchema2yaml.handleFileUpload(formWrapper, redirectAttributes);
			redirectAttributes.addFlashAttribute("message",
					"API Artifacts generated successfully for " + formWrapper.getFile().getOriginalFilename() + "!");
			return "redirect:/";
		} catch (APIBuilderException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			redirectAttributes.addFlashAttribute(error, "FlashAttribute");
			model.addAttribute(error, e.getMessage());
			return indexPage;
		}

		
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	public static String getYAML(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

}
