package jp.co.keepalive.springbootfw.util.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestHeaders implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<NameValuePair> params;

    private static final String HEADER_KEY_COOKIE = "Cookie";

    public HttpRequestHeaders(){
        params = new ArrayList<NameValuePair>();
    }

    public void addCookie(String value){
        params.add(new BasicNameValuePair(HEADER_KEY_COOKIE, value));
    }

    public void addParam(String name, String value){
        params.add(new BasicNameValuePair(name, value));
    }

    public void addParam(String name, int value){
        params.add(new BasicNameValuePair(name, String.valueOf(value)));
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

}