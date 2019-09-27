package com.cg.osce.apibuilder.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cg.osce.apibuilder.exception.APIBuilderException;
import com.sun.codemodel.JCodeModel;

@Service
public class PojoHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(IYamlGeneratorServiceImpl.class);

	void createPojoForXSD(MultipartFile xsd) throws APIBuilderException {

		String[] command = { "cmd", };
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);

			new Thread(new ErrorPipe(p.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());
			stdin.println("xjc -nv -d src/main/java -p com.cg.osce.apibuilder.entity xsd/" + xsd.getOriginalFilename());

			stdin.close();
			if (p.getErrorStream().read() != -1) {
				throw new APIBuilderException(128, "Provided Schema is not valid");
			}
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new APIBuilderException(128, "Failed to process provided schema");
		}
	}
	
	public void convertPojoForJSON(MultipartFile json) throws APIBuilderException {
		String userDir = System.getProperty("user.dir").replace("\\", "/");
		java.net.URL source;
		try {
			source = new File(userDir + "/xsd/" + json.getOriginalFilename()).toURI().toURL();

			File outputPojoDirectory = new File(userDir + "/src/main/java/");
			JCodeModel codeModel = new JCodeModel();
			GenerationConfig config = new DefaultGenerationConfig() {
				@Override
				public boolean isGenerateBuilders() { // set config option by overriding method
					return true;
				}

				@Override
				public boolean isIncludeAdditionalProperties() {
					return false;
				}

				@Override
				public SourceType getSourceType() {
					return SourceType.JSONSCHEMA;
				}
			};
			SchemaMapper schemaMapper = new SchemaMapper(
					new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());

			schemaMapper.generate(codeModel, json.getOriginalFilename().replace(".json", ""), "com.cg.osce.apibuilder.entity", source);
			codeModel.build(outputPojoDirectory);
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new APIBuilderException(128, "Failed to Process provided JSON schema");
		}

	}
}
