package com.cg.osce.apibuilder.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cg.osce.apibuilder.pojo.Constants;
import com.cg.osce.apibuilder.pojo.Delete;
import com.cg.osce.apibuilder.pojo.Get;
import com.cg.osce.apibuilder.pojo.Parameter;
import com.cg.osce.apibuilder.pojo.Post;
import com.cg.osce.apibuilder.pojo.Put;
import com.cg.osce.apibuilder.pojo.Response200;
import com.cg.osce.apibuilder.pojo.Response400;
import com.cg.osce.apibuilder.pojo.Response404;
import com.cg.osce.apibuilder.pojo.Response405;
import com.cg.osce.apibuilder.pojo.Responses;
import com.cg.osce.apibuilder.pojo.Schema;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class YamlHelper {

	@Value("${package.name}")
	private String packageName;

	private static final Logger LOGGER = LoggerFactory.getLogger(IYamlGeneratorServiceImpl.class);

	public Post createDefaultPost(Class<? extends Object> entity, JsonNodeFactory factory) {
		String entityName = entity.getSimpleName();
		Post post = new Post();
		List<String> consumes = new ArrayList<>();
		consumes.add(Constants.XMLAPPLICATION);
		consumes.add(Constants.JSONAPPLICATION);
		List<String> produces = new ArrayList<>();
		produces.add(Constants.XMLAPPLICATION);
		produces.add(Constants.JSONAPPLICATION);
		post.setConsumes(consumes);
		post.setProduces(produces);
		post.setSummary(Constants.POSTSUMMARY + entity.getSimpleName());
		post.setDescription( Constants.POSTDESCRIPTION+" "+entityName+" records in the system");
		post.setOperationId("post" + entity.getSimpleName());
		Responses responses = new Responses();
		responses.set400(new Response400());
		responses.set200(new Response200());
		post.setResponses(responses);
		List<Parameter> parameters = new ArrayList<>();
		Parameter parameter = new Parameter();
		parameter.setDescription(entity.getSimpleName() + Constants.POSTDESCRIPTION);
		parameter.setIn("body");
		parameter.setName(entity.getSimpleName());
		parameter.setRequired(true);
		Schema schema = new Schema();
		schema.set$ref(Constants.SET$REF + entity.getSimpleName());
		parameter.setSchema(schema);
		parameters.add(parameter);
		post.setParameters(parameters);
		return post;
	}

	public void setParameters(Class<? extends Object> entity, List<Parameter> parameters) {
		for (Field field : entity.getDeclaredFields()) {

			String type = getType(field.getType().getSimpleName());
			if (!"default".equals(type) && field.getName().toLowerCase().contains("id")) {
				Parameter parameter = new Parameter();
				parameter.setName(field.getName());
				parameter.setIn("query");
				parameter.setDescription(field.getName() + " Entity to be Fetched");
				parameter.setRequired(true);
				parameter.setType(type);
				parameters.add(parameter);
			}

		}

	}

	public Get createDefaultGet(Class<? extends Object> entity, JsonNodeFactory factory) {

		String entityName = entity.getSimpleName();
		Get get = new Get();
		get.setSummary(Constants.GETSUMMARY +" "+entityName+" from the system");
		get.setDescription(Constants.GETDESCRIPTION+" "+entityName+" records from the system");
		get.setOperationId("get" + entityName);
		List<Parameter> parameters = new ArrayList<>();

		Parameter limitParam = new Parameter();
		limitParam.setName("limit");
		limitParam.setIn(Constants.QUERY);
		limitParam.setDescription("Maximum number of elements per page");
		limitParam.setRequired(true);
		limitParam.setType(Constants.INTEGER);
		parameters.add(limitParam);

		Parameter queryParam = new Parameter();
		queryParam.setName("page");
		queryParam.setIn(Constants.QUERY);
		queryParam.setDescription("Page number");
		queryParam.setRequired(true);
		queryParam.setType(Constants.INTEGER);
		parameters.add(queryParam);
		// ----Based on the id ------
		setParameters(entity, parameters);// ............................

		get.setParameters(parameters);
		List<String> produces = new ArrayList<>();
		produces.add(Constants.XMLAPPLICATION);
		produces.add(Constants.JSONAPPLICATION);
		get.setProduces(produces);

		Responses responses = new Responses();
		Response200 response200 = new Response200();
		response200.setDescription("Successful Response");
		ObjectNode schema = factory.objectNode();
		schema.put("type", "object");
		// entity.p
		ObjectNode properties = factory.objectNode();
		ObjectNode page = factory.objectNode();
		page.put("type", Constants.INTEGER);
		page.put(Constants.EXAMPLE, 5);
		properties.set("page", page);

		ObjectNode limit = factory.objectNode();
		limit.put("type", Constants.INTEGER);
		limit.put(Constants.EXAMPLE, 10);
		properties.set("limit", limit);

		ObjectNode totalPages = factory.objectNode();
		totalPages.put("type", Constants.INTEGER);
		totalPages.put(Constants.EXAMPLE, 10);
		properties.set("totalPages", totalPages);

		ObjectNode totalRecords = factory.objectNode();
		totalRecords.put("type", Constants.INTEGER);
		totalRecords.put(Constants.EXAMPLE, 100);
		properties.set("totalRecords", totalRecords);

		ObjectNode pojo = factory.objectNode();
		pojo.put("type", "array");
		ObjectNode items = factory.objectNode();
		items.put("$ref", Constants.SET$REF + entity.getSimpleName());
		pojo.putPOJO("items", items);
		properties.set("totalRecords", totalRecords);

		properties.putPOJO("schema", pojo);

		schema.set("properties", properties);
		response200.setSchema(schema);

		responses.setResponse200(response200);
		get.setResponses(responses);

		return get;
	}

	public Put createDefaultPut(Class<? extends Object> entity, JsonNodeFactory factory) {
		String entityName = entity.getSimpleName();

		Put put = new Put();

		put.setSummary("Update an existing " + entity.getSimpleName());
		put.setDescription(entityName + "to be Updated");

		put.setOperationId("update" + entity.getSimpleName());
		List<String> consumes = new ArrayList<>();
		consumes.add(Constants.XMLAPPLICATION);
		consumes.add(Constants.JSONAPPLICATION);
		List<String> produces = new ArrayList<>();
		produces.add("application/json");
		produces.add("application/xml");
		put.setConsumes(consumes);
		put.setProduces(produces);
		List<Parameter> parameters = new ArrayList<>();
		Parameter parameter = new Parameter();
		parameter.setIn("body");
		parameter.setName("body");
		parameter.setDescription(entity.getSimpleName() + "object that needs to be added to the store");
		parameter.setRequired(true);
		Schema schema = new Schema();
		schema.set$ref(Constants.SET$REF + entity.getSimpleName());
		parameter.setSchema(schema);
		parameters.add(parameter);
		put.setParameters(parameters);
		Responses responses = new Responses();
		Response200 response200 = new Response200();
		response200.setDescription("Successful Response");
		responses.setResponse200(response200);
		responses.setResponse400(new Response400());
		responses.setResponse404(new Response404());
		responses.setResponse405(new Response405());
		put.setResponses(responses);

		return put;

	}
	// ----------End of Put-------------

	public Delete createDefaultDelete(Class<? extends Object> entity, JsonNodeFactory factory) {
		String entityName = entity.getSimpleName();

		Delete delete = new Delete();
		delete.setSummary("Deletes a " + entity.getSimpleName());
		delete.setDescription(entityName + "to be Deleted");

		delete.setOperationId("delete" + entity.getSimpleName());
		List<String> produces = new ArrayList<>();
		produces.add("application/json");
		produces.add("application/xml");
		delete.setProduces(produces);
		List<Parameter> parameters = new ArrayList<>();

		Parameter limitParam = new Parameter();
		limitParam.setName("api_key");
		limitParam.setIn("header");
		limitParam.setRequired(false);
		limitParam.setType(Constants.STRING);
		parameters.add(limitParam);

		setParameters(entity, parameters);
		delete.setParameters(parameters);
		Responses responses = new Responses();
		Response200 response200 = new Response200();
		response200.setDescription("Successful Response");
		responses.setResponse400(new Response400());
		responses.setResponse404(new Response404());
		delete.setResponses(responses);

		return delete;

	}

	String getType(String type) {

		String fieldType = type.toLowerCase();

		if (Constants.STRING.equals(fieldType) || "list".equals(fieldType) || "xmlgregoriancalendar".equals(fieldType)
				|| "jaxbelement<string>".equals(fieldType)) {
			return Constants.STRING;
		} else if (fieldType.contains("int") || "biginteger".equals(fieldType) || "bigdecimal".equals(fieldType)
				|| "jaxbelement<int>".equals(fieldType)) {
			return Constants.INTEGER;
		} else if (Constants.BOOLEAN.equals(fieldType)) {
			return Constants.BOOLEAN;
		} else if (Constants.OBJECT.equals(fieldType)) {
			return Constants.OBJECT;
		} else {
			return "string";
		}
	}

	private ObjectNode setProperty(Field field, JsonNodeFactory factory) {
		ObjectNode property = factory.objectNode();
		String type = field.getType().getSimpleName();

		switch (type.toLowerCase()) {

		case Constants.STRING:
			property.put("type", Constants.STRING);
			property.put(Constants.EXAMPLE, "helloworld");
			break;
		case "long":
			property.put("type", Constants.INTEGER);
			property.put(Constants.FORMAT, "int64");
			property.put(Constants.EXAMPLE, 1253465876);
			break;
		case "short":
			property.put("type", Constants.INTEGER);
			property.put(Constants.FORMAT, "int");
			property.put(Constants.EXAMPLE, 234);
			break;
		case "byte":
			property.put("type", Constants.INTEGER);
			property.put(Constants.FORMAT, "int");
			property.put(Constants.EXAMPLE, 127);
			break;
		case "char":
			property.put("type", "string");
			property.put(Constants.EXAMPLE, 'A');
			break;
		case "biginteger":
			property.put("type", Constants.INTEGER);
			property.put(Constants.FORMAT, "int32");
			property.put(Constants.EXAMPLE, 6598741);
			break;
		case Constants.INTEGER:
			property.put("type", "integer");
			property.put(Constants.EXAMPLE, 6598);
			break;
		case "int":
			property.put("type", "integer");
			property.put(Constants.EXAMPLE, 6985);
			break;
		case "double":
			property.put("type", Constants.NUMBER);
			property.put(Constants.FORMAT, "double");
			property.put(Constants.EXAMPLE, 12.7643);
			break;
		case "float":
			property.put("type", "number");
			property.put("format", "float");
			property.put(Constants.EXAMPLE, 12.85);
			break;
		case "list":
			ObjectNode items = factory.objectNode();
			property.put("type", "array");
			Type type1 = field.getGenericType();
			if (type1 instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type1;
				for (Type t : pt.getActualTypeArguments()) {
					String arrayType = t.getTypeName().replace("" + packageName, "");
					if (arrayType.contains("java")) {
						int length = arrayType.split("\\.").length;
						items.put("type", getType(arrayType.split("\\.")[length - 1]));
						// TODO: Generate Examples for Map based on the type
					} else {
						String arrayTypeName = t.getTypeName().replace("" + packageName, "");
						if (arrayTypeName.contains("$")) {
							int length = arrayTypeName.split("\\$").length;
							arrayTypeName = arrayTypeName.split("\\$")[length - 1];
						}
						items.put("$ref", "#/definitions/" + arrayTypeName);
					}
				}
			}
			property.putPOJO("items", items);
			break;
		case "boolean":
			property.put("type", "boolean");
			property.put(Constants.EXAMPLE, "true");
			break;
		case "bigdecimal":
			property.put("type", "number");
			property.put("example", 655498.5456);
			break;
		case "xmlgregoriancalendar":
			property.put("type", "string");
			property.put("format", "date");
			property.put("example", 12252019);
			break;
		case "map":
			property.put("type", "object");
			ObjectNode additionalProperties = factory.objectNode();
			Type mapField = field.getGenericType();
			if (mapField instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) mapField;
				String mapType = pt.getActualTypeArguments()[1].getTypeName();
				if (mapType.contains("java")) {
					int length = mapType.split("\\.").length;
					additionalProperties.put("type", getType(mapType.split("\\.")[length - 1]));
				} else {
					String mapTypeName = mapType.replace("" + packageName, "");
					if (mapTypeName.contains("$")) {
						int length = mapTypeName.split("\\$").length;
						mapTypeName = mapTypeName.split("\\$")[length - 1];
					}
					additionalProperties.put("$ref", "#/definitions/" + mapTypeName);
				}
				property.putPOJO("additionalProperties", additionalProperties);
				break;
			}
			break;
		// YEt to work

		default:
			property.put("$ref", "#/definitions/" + type);
			break;
		}
		return property;
	}

	@SuppressWarnings("deprecation")

	public ObjectNode creteDefinitions(JsonNodeFactory factory) {

		Set<Class<? extends Object>> allClasses = getClassDetails();

		Set<Class<? extends Enum>> allEnums = getEnumDetails();

		ObjectNode schema = factory.objectNode();

		for (Class<? extends Object> obj : allClasses) {
			if ("ObjectFactory".equals(obj.getSimpleName()) || "package-info".equals(obj.getSimpleName()))
				continue;
			ObjectNode entity = factory.objectNode();
			entity.put("type", "object");
			// entity.p
			ObjectNode properties = factory.objectNode();
			for (Field field : obj.getDeclaredFields()) {
				// setProperty based on fieldType
				properties.set(field.getName(), setProperty(field, factory));
			}
			entity.set("properties", properties);
			schema.set(obj.getSimpleName(), entity);

		}
		for (Class<? extends Enum> enumEntity : allEnums) {
			ObjectNode entity = factory.objectNode();
			entity.put("type", Constants.STRING);
			ArrayNode arrayNode = factory.arrayNode();
			for (Field field : enumEntity.getDeclaredFields()) {
				// setProperty based on fieldType
				arrayNode.add(field.getName());
			}
			entity.put("enum", arrayNode);
			schema.set(enumEntity.getSimpleName(), entity);

		}
		LOGGER.info(schema.toString());
		return schema;
	}


	public Set<Class<? extends Object>> getClassDetails() {
		Reflections reflections = new Reflections("com.cg.osce.apibuilder.entity", new SubTypesScanner(false));
		return reflections.getSubTypesOf(Object.class);

	}
	public Set<Class<? extends Enum>> getEnumDetails() {
		Reflections reflections = new Reflections("com.cg.osce.apibuilder.entity", new SubTypesScanner(false));
		return reflections.getSubTypesOf(Enum.class);

	}
}
