package jp.co.edi_java.app.util.xml.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElementBean {

    private Map<String, Object> map = new HashMap<String, Object>();

    private AttributeBean attr = new AttributeBean();

    public void addChild(String key) {
        ElementBean bean = new ElementBean();
        map.put(key, bean);
    }

    public void addElement(String key, Object value) {
        map.put(key, value);
    }

    public Object getElement(String key) {
        return map.get(key);
    }

    public String getValue(String key) {
        return (String) map.get(key);
    }

    public ElementBean getChild(String key) {
        return (ElementBean) map.get(key);
    }

    public void addAttribute(String key, String value) {
        attr.addAttribute(key, value);
    }

    public Set<String> getKeyList() {
        return map.keySet();
    }

    public AttributeBean getAttribute() {
        return attr;
    }
}