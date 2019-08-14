package jp.co.keepalive.springbootfw.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jp.co.edi_java.app.service.MailService;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.logging.SystemLoggingUtil;
import jp.co.keepalive.springbootfw.util.mail.MailUtils;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	private String adminEmail = "t-iida@tamahome.jp, to-suzuki@tamahome.jp, shinji-yamaguchi@tamahome.jp";
	//private String adminEmail = "shinji-yamaguchi@tamahome.jp";

    @ExceptionHandler(CoreRuntimeException.class)
    public ResponseEntity handleCoreRuntimeException(CoreRuntimeException exception) {
    	ResponseEntity response = new ResponseEntity();
    	response.resultStatus = exception.getStatusCode();
    	response.data.put("action", getClass().getName());
    	response.data.put("errors", exception.getClass());
    	response.data.put("message", exception.getMessage());
    	String msg = SystemLoggingUtil.getStackTraceString(exception);
    	if(log.isDebugEnabled()) {
    		response.data.put("stackTrace", msg);
    	}
    	log.error(msg);
    	MailUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemErrSubject(), msg);
        return response;
    }

    @ExceptionHandler({RuntimeException.class, NullPointerException.class})
    public ResponseEntity handleRuntimeException(RuntimeException exception) {
    	ResponseEntity response = new ResponseEntity();
    	response.resultStatus = ResponseCode.ERROR_CODE_500;
    	response.data.put("action", getClass().getName());
    	response.data.put("errors", exception.getClass());
    	response.data.put("message", exception.getMessage());
    	String msg = SystemLoggingUtil.getStackTraceString(exception);
    	if(log.isDebugEnabled()) {
    		response.data.put("stackTrace", msg);
    	}
    	log.error(msg);
    	MailUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemErrSubject(), msg);
        return response;
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException exception, BindingResult bindingResult) {
    	ResponseEntity response = new ResponseEntity();
    	response.resultStatus = ResponseCode.ERROR_CODE_400;
    	response.data.put("action", getClass().getName());
    	response.data.put("errors", exception.getClass());
    	response.data.put("message", exception.getMessage());
    	response.data.put("detail", bindingResult.getAllErrors());
    	String msg = SystemLoggingUtil.getStackTraceString(exception);
    	if(log.isDebugEnabled()) {
    		response.data.put("stackTrace", msg);
    	}
    	MailUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemErrSubject(), msg);
        return response;
    }
}