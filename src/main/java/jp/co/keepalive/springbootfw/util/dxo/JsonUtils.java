package jp.co.keepalive.springbootfw.util.dxo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class JsonUtils {

    private static ObjectMapper mapper =  new ObjectMapper();

    public static String encode(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static Map<String, Object> decode(String json) {
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static List<Map<String, Object>> decodeList(String json) {
        TypeReference<ArrayList<HashMap<String, Object>>> typeRef = new TypeReference<ArrayList<HashMap<String, Object>>>() {
        };
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static List<String> decodeStringList(String json) {
        TypeReference<ArrayList<String>> typeRef = new TypeReference<ArrayList<String>>() {
        };
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

}
