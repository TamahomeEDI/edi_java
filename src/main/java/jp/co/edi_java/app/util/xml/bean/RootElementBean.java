package jp.co.edi_java.app.util.xml.bean;

public class RootElementBean {

    private String rootName = null;

    private ElementBean child = null;

    public RootElementBean (String rootName) {
        this.rootName = rootName;
        this.child = new ElementBean();
    }

    public String getRootName() {
        return rootName;
    }

    public ElementBean getChild() {
        return child;
    }
}