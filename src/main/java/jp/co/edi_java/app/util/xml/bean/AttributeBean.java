package jp.co.edi_java.app.util.xml.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AttributeBean {

    private Map<String, String> attr = new HashMap<String, String>();

    public void addAttribute(String key, String value) {
        attr.put(key, value);
    }

    public Set<String> getKeyList() {
        return attr.keySet();
    }

    public String getValue(String key) {
        return attr.get(key);
    }
}