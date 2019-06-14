package jp.co.edi_java.app.util.xml;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class DomParser {

    /*
     * 子ノードの値をリストで返却する
     */
    public static Map<String, Object> parseToMap(String xml) {
        Document doc = xmlStringToDocument(xml);
        return parse(doc);
    }

    private static Document xmlStringToDocument(String xml) {
        Document document = null;
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
            document = builder.parse(stream);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
        return document;
    }

    private static Map<String, Object> parse(Document xml) {
        Map<String, Object> ret = new HashMap<>();
        Element root = xml.getDocumentElement();
        Map<String, Object> child = parseChild(root.getChildNodes());
        ret.put(root.getNodeName(), child);
        return ret;
    }

    @SuppressWarnings("unchecked")
    private static final Map<String, Object> parseChild(NodeList nodeList) {
        Map<String, Object> ret = new HashMap<>();
        for(int i=0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList childList = node.getChildNodes();
            if(childList.getLength() == 0) {
                continue;
            } else if(childList.getLength() == 1 && childList.item(0).getNodeName().equals("#text")) {
                ret.put(node.getNodeName(), childList.item(0).getNodeValue());
            } else {
                if(!ret.containsKey(node.getNodeName())) {
                    ret.put(node.getNodeName(), parseChild(childList));
                } else {
                    Object obj = ret.get(node.getNodeName());
                    if(obj instanceof List) {
                        ((List) obj).add(parseChild(childList));
                    } else {
                        List<Object> list = new ArrayList<>();
                        list.add(obj);
                        list.add(parseChild(childList));
                        ret.put(node.getNodeName(), list);
                    }

                }
            }
        }
        return ret;
    }

    private static List<Node> getChildNodeList(Document document, String rootName, String childName) {
        List<Node> retList = new ArrayList<Node>();
        if (document.hasChildNodes()) {
            Node node = getRootNode(document, rootName);
            //ルートノード
            if(node != null) {
                NodeList list = node.getChildNodes();
                for(int i=0; i<list.getLength(); i++) {
                    Node child = list.item(i);
                    //子ノード
                    if(child.getNodeName().equals(childName)) {
                        retList.add(child);
                    }
                }
            } else {
                throw new RuntimeException("XMLファイルが不正です。");
            }
       } else {
           throw new RuntimeException("XMLファイルが不正です。");
       }
       return retList;
    }

    private static Node getRootNode(Document doc, String rootName) {
        Node node = null;
        NodeList list = doc.getChildNodes();
        for(int i=0; i<list.getLength(); i++) {
            node = list.item(i);
            if(node.getNodeName().equals(rootName)) {
                return node;
            }
        }
        return null;
    }

    private static List<Map<String, String>> convertNodesToMaps(List<Node> nodeList) {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        for(int i=0; i<nodeList.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            Node node = nodeList.get(i);
            //ノード値をMapにセット
            map.put(node.getNodeName(), node.getFirstChild().getNodeValue());
            //属性値をMapにセット
            NamedNodeMap attributes = node.getAttributes();
            for(int n=0; n<attributes.getLength(); n++) {
                Node attr = attributes.item(n);
                map.put(attr.getNodeName(), attr.getNodeValue());
            }
            retList.add(map);
        }
        return retList;
    }

    private static List<String> convertNodesToStrings(List<Node> nodeList) {
        List<String> retList = new ArrayList<String>();
        for(int i=0; i<nodeList.size(); i++) {
            Node node = nodeList.get(i);
            retList.add(node.getFirstChild().getNodeValue());
        }
        return retList;
    }

    public static Map<String, Object> parse(Document doc, String rootName) {
        NodeList nodeList = doc.getElementsByTagName(rootName);
        return parseChildList(nodeList.item(0).getChildNodes());
    }


    public static List<Map<String, Object>> parseList(Document doc, String nodeName) {
        List<Map<String, Object>> parseList = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName(nodeName);

        //タグリスト
        for(int i=0; i<nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            //タグ内解析
            NodeList childList = item.getChildNodes();
            parseList.add(parseChildList(childList));
        }

        return parseList;
    }


    private static Map<String, Object> parseChildList(NodeList childList) {
        Map<String, Object> ret = new HashMap<>();

        //子ノードリスト
        for(int i=0; i<childList.getLength(); i++) {
            Node child = childList.item(i);

            if(child.getNodeName().equals("#text")) {
                continue;
            }

            //子ノード有無判定
            if(hasChlid(child)) {
                mergePut(ret, child.getNodeName(), parseChildList(child.getChildNodes()));
                continue;
            }

            //属性無し
            if(!child.hasAttributes()) {
                if(child.getFirstChild() != null) {
                    mergePut(ret, child.getNodeName(), child.getFirstChild().getNodeValue());
                } else {
                    mergePut(ret, child.getNodeName(), "");
                }

            //属性有り
            } else {
                NamedNodeMap attrList = child.getAttributes();
                Map<String, String> map = new HashMap<>();
                for(int l=0; l<attrList.getLength(); l++) {
                    Node attr = attrList.item(l);
                    map.put(attr.getNodeName(), attr.getNodeValue());

                }
                if(child.getFirstChild() != null) {
                    map.put("value", child.getFirstChild().getNodeValue());
                }
                mergePut(ret, child.getNodeName(), map);
            }
        }
        return ret;
    }

    private static boolean hasChlid(Node node) {
        NodeList magoList = node.getChildNodes();
        for(int x=0; x<magoList.getLength(); x++) {
            Node mago = magoList.item(x);
            if(!mago.getNodeName().equals("#text")) {
                return true;
            }
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    protected static void mergePut(Map<String, Object> map, String key, Object val) {
        if(!map.containsKey(key)) {
            map.put(key, val);
        } else {
            Object old = map.get(key);
            if(old instanceof List) {
                ((List<Object>)old).add(val);
                map.put(key, old);
            } else {
                List<Object> list = new ArrayList<>();
                list.add(old);
                list.add(val);
                map.put(key, list);
            }
        }
    }

}
