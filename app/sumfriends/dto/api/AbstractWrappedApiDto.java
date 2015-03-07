package sumfriends.dto.api;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class AbstractWrappedApiDto {
    public JsonNode toJson(){
        return Json.newObject().set(_getWrapperName(), Json.toJson(this));
    }
    protected abstract String _getWrapperName();
}
