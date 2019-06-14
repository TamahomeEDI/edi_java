package jp.co.edi_java.app.util.xml;

import java.io.StringWriter;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import jp.co.edi_java.app.util.xml.bean.ElementBean;
import jp.co.edi_java.app.util.xml.bean.RootElementBean;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class XMLDocumentBuilder {

    private XMLDocumentBuilder() {

    }

    public static Document createXMLDocument(String root) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new CoreRuntimeException(e);
        }

        DOMImplementation dom = builder.getDOMImplementation();
        return dom.createDocument("", root, null);
    }

    public static String createXMLString(Document document) {
        StringWriter writer = new StringWriter();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
        return writer.toString();
    }

    public static String createXmlString(RootElementBean ele) {
        Document doc = createXmlDocument(ele);
        StringWriter sw = new StringWriter();
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tfactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
        } catch (Exception e) {
            throw new CoreRuntimeException();
        }
        return sw.toString();
    }

    public static Document createXmlDocument(RootElementBean ele) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new CoreRuntimeException(e);
        }
        Document document = builder.newDocument();
        Element rootElement = document.createElement(ele.getRootName());
        document.appendChild(rootElement);

        ElementBean child = ele.getChild();
        setElementToDoc(rootElement, child, document);
        return document;
    }

    private static void setElementToDoc(Element parent, ElementBean bean, Document doc) {
        Set<String> keys = bean.getKeyList();
        for (String key : keys) {
            Object obj = bean.getElement(key);
            if(obj == null) {
                continue;
            }
            if(obj instanceof String) {
                Element child = doc.createElement(key);
                parent.appendChild(child);
                Text text = doc.createTextNode((String)obj);
                child.appendChild(text);
            } else if (obj instanceof ElementBean){
                Element child = doc.createElement(key);
                parent.appendChild(child);
                setElementToDoc(child, (ElementBean) obj, doc);
            } else {
                throw new CoreRuntimeException();
            }
            Set<String> attrKeys = bean.getAttribute().getKeyList();
            for (String attrKey : attrKeys) {
                parent.setAttribute(attrKey, bean.getAttribute().getValue(attrKey));
            }
        }
    }
}
