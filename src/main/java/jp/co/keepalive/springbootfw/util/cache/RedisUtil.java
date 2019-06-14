package jp.co.keepalive.springbootfw.util.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;

@Component
public class RedisUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public static final int EXPIRE_DAY = 60*60*24;
    public static final int EXPIRE_HOUR = 60*60;

    public boolean exists( String key ) {
    	return stringRedisTemplate.hasKey(key);
    }

    public void set( String key, Object value ) {
    	stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    public void set( String key, Object value, int sec ) {
    	stringRedisTemplate.opsForValue().set(key, String.valueOf(value), sec, TimeUnit.SECONDS);
    }

    public String get( String key ) {
    	return stringRedisTemplate.opsForValue().get(key);
    }

    public Map<String, Object> getMap( String key ) {
        String ret = stringRedisTemplate.opsForValue().get(key);
        return JsonUtils.decode(ret);
    }

    public List<Map<String,Object>> getList( String key ) {
        String ret = stringRedisTemplate.opsForValue().get(key);
        return JsonUtils.decodeList(ret);
    }

    public List<String> getStringList( String key ) {
        String ret = stringRedisTemplate.opsForValue().get(key);
        return JsonUtils.decodeStringList(ret);
    }

    public Boolean del( String key ) {
        return stringRedisTemplate.delete(key);
    }

    public long delList( String keyPattern ) {
        Set<String> ret = stringRedisTemplate.keys(keyPattern);
        for (String key : ret) {
        	stringRedisTemplate.delete(key);
        }
        return ret.size();
    }
}
