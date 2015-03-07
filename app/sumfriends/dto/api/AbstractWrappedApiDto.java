package sumfriends.dto.api;

import java.util.HashMap;
import java.util.Map;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractWrappedApiDto {
	public final Map<String, String> links = new HashMap<>();
	protected void addLink(String name, String path){
		this.links.put(name, "/api/v1" + path);
	}
    public JsonNode toJson(){
        return Json.newObject().set(_getWrapperName(), Json.toJson(this));
    }
    protected abstract String _getWrapperName();
}
