package jp.co.keepalive.springbootfw.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Transactional
public class BaseController {

    protected ResponseEntity response = new ResponseEntity();

    protected Log LOGGER = LogFactory.getLog(this.getClass());

    public BaseController(){
        if(LOGGER.isDebugEnabled()){
        	response.data.put("action", getClass().getName());
        }
    }

    protected void setResultCount(int num){
    	response.data.put("resultCount", Integer.toString(num));
    }

    protected void setError(String key, String msg){
    	response.data.put(key, msg);
    }

    protected void setErrorCode(String key){
    	response.resultStatus = key;
    	response.data.put("message", ResponseCode.errorResponseCodeSet.get(key));
    }

    protected void setResponseData(String key, Object value){
    	response.data.put(key, value);
    }

    protected Object getResponseData(String key){
        return response.data.get(key);
    }

    protected ResponseEntity response(){
        if(StringUtils.isNullString(response.resultStatus)){
        	response.resultStatus = ResponseCode.NORMAL_CODE_200;
        }
        return response;
    }

    protected void handleException(Throwable th){
    	response.data.put("resultStatus", ResponseCode.ERROR_CODE_500);
        if(LOGGER.isDebugEnabled()){
            setError("stackTrace", th.getMessage());
        }
        response();
    }

}