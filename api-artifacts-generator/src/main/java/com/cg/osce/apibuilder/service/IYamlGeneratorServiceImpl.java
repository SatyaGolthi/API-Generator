package com.cg.osce.apibuilder.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cg.osce.apibuilder.FormWrapper;
import com.cg.osce.apibuilder.exception.APIBuilderException;
import com.cg.osce.apibuilder.pojo.Constants;
import com.cg.osce.apibuilder.pojo.SwaggerSchema;
import com.cg.osce.apibuilder.pojo.SwaggerSchema.Info;
import com.cg.osce.apibuilder.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

@Service
public class IYamlGeneratorServiceImpl implements IYamlGeneratorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IYamlGeneratorServiceImpl.class);

	@Autowired
	StorageService storageService;

	@Autowired
	PojoHelper generator;

	@Autowired
	YamlHelper yamlGenerator;
	
	@Value("${package.name}")
	private String packageName;

	static final JsonNodeFactory factory = JsonNodeFactory.instance;
	private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));

	@Override
	public void handleFileUpload(FormWrapper formWrapper, RedirectAttributes redirectAttributes)
			throws APIBuilderException {
		clearDirectories();
		storageService.store(formWrapper.getFile());

		if (formWrapper.getFile().getOriginalFilename().endsWith(".xsd")) {
			generator.createPojoForXSD(formWrapper.getFile());
		} else if (formWrapper.getFile().getOriginalFilename().endsWith(".json")) {
			generator.convertPojoForJSON(formWrapper.getFile());
		}

		compilePojos();

		printYAML(formWrapper);

	}

	void clearDirectories() throws APIBuilderException {
		String userDir = System.getProperty("user.dir").replace("\\", "/");
		File schemaDirectory = new File(userDir + "/xsd/");
		File entityDirectory = new File(userDir + "/src/main/java/com/cg/osce/apibuilder/entity");
		File entityClassDirectory = new File(userDir + "/target/classes/com/cg/osce/apibuilder/entity");
		try {
			if (entityClassDirectory.exists()) {
				FileUtils.cleanDirectory(entityClassDirectory);
			}
			if (entityDirectory.exists()) {
				FileUtils.cleanDirectory(entityDirectory);
			}
			if (schemaDirectory.exists()) {
				FileUtils.cleanDirectory(schemaDirectory);
			}

		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new APIBuilderException(128, "Failed to clear directories");
		}
	}

	void compilePojos() throws APIBuilderException {
		String userDir = System.getProperty("user.dir").replace("\\", "/");
		String[] command = { "cmd", };
		String jarPath = null;
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);

			new Thread(new ErrorPipe(p.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());

			stdin.println("cd src/main/java/com/cg/osce/apibuilder/entity/");

			jarPath = "-classpath \"" + System.getProperty("java.class.path") + "\"";
			stdin.println("javac " + jarPath + " *.java");
			stdin.println("move *.class \"" + userDir + "/target/classes/com/cg/osce/apibuilder/entity/\"");
			stdin.close();
			if (p.getErrorStream().read() != -1) {
				throw new APIBuilderException(128, "Provided Schema is not valid");
			}
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			throw new APIBuilderException(128, "Failed to Process provided schema");
		}
	}

	public SwaggerSchema printYAML(FormWrapper request) throws APIBuilderException {

		SwaggerSchema obj = new SwaggerSchema();
		obj.setSwagger(request.getSwaggerVersion());
		Info info = new Info();
		info.setVersion(request.getSwaggerVersion());

		info.setTitle(request.getTitle());
		info.setDescription(request.getDescription());
		obj.setInfo(info);
		obj.setBasePath(request.getBasepath());
		obj.setHost(request.getHost());
		Set<Class<? extends Object>> entities = yamlGenerator.getClassDetails();

		ObjectNode paths = factory.objectNode();
		for (Class<? extends Object> entity : entities) {
			if ("ObjectFactory".equals(entity.getSimpleName()) || "package-info".equals(entity.getSimpleName()))
				continue;

			ObjectNode path = factory.objectNode();

			path.putPOJO("get", yamlGenerator.createDefaultGet(entity, factory));
			path.putPOJO("post", yamlGenerator.createDefaultPost(entity, factory));
			path.putPOJO("put", yamlGenerator.createDefaultPut(entity, factory));
			path.putPOJO("delete", yamlGenerator.createDefaultDelete(entity, factory));
			paths.set("SLASH" + entity.getSimpleName().toLowerCase(), path);
		}
		obj.setSchemes(new String[] { "http", "https" });

		obj.setPaths(paths);

		obj.setDefinitions(yamlGenerator.creteDefinitions(factory));
		try {
			FileWriter fw = new FileWriter(Constants.FILELOCATION);
			fw.write(mapper.writeValueAsString(obj).replace("SLASH", "/"));
			fw.close();
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new APIBuilderException(123, "Failed to create and write API Document");
		}
		return obj;

	}

}
