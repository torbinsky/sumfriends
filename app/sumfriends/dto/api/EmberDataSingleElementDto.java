package sumfriends.dto.api;

import java.util.HashMap;
import java.util.Map;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class EmberDataSingleElementDto {	
	public final Map<String, String> links = new HashMap<>();

	public EmberDataSingleElementDto() {
		super();
	}

	public JsonNode toJson() {
		return Json.newObject().set(_getWrapperName(), Json.toJson(this));
	}
	protected void addLink(String name, String path) {
		links.put(name, path);
	}
	protected abstract String _getWrapperName();
}
