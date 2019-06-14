package jp.co.keepalive.springbootfw.util.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<NameValuePair> params;

    public HttpRequestParams(){
        params = new ArrayList<NameValuePair>();
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