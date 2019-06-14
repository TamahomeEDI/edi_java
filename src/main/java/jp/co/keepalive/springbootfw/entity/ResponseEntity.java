package jp.co.keepalive.springbootfw.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity {

	public String resultStatus;

	public Map<String, Object> data = new HashMap<String, Object>();

}