package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.TExclusiveDao;
import jp.co.edi_java.app.entity.TExclusiveEntity;
import jp.co.edi_java.app.form.ExclusiveForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class ExclusiveService {

	@Autowired
    public TExclusiveDao tExclusiveDao;

	private static final String SUCCESS_KEY = "success";
	private static final String FAIL_KEY = "fail";
	private static final String STG_FLG_ON = "1";
	private static String STG_FLG;
	private static long LOCK_LIMIT_TIME = 600000;

	private ExclusiveService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public String getSuccessKey () {
		return SUCCESS_KEY;
	}

	public String getFailKey () {
		return FAIL_KEY;
	}

	//ロック情報の取得
	public Map<String,List<TExclusiveEntity>> getLock(ExclusiveForm form) {
		Map<String,List<TExclusiveEntity>> result = new HashMap<String,List<TExclusiveEntity>>();
		List<TExclusiveEntity> lockedList = new ArrayList<TExclusiveEntity>();
		List<TExclusiveEntity> lockFailedList = new ArrayList<TExclusiveEntity>();

		if (Objects.nonNull(form) && Objects.nonNull(form.getExclusiveObjectName())
				&& Objects.nonNull(form.getExclusiveObjectKey()) && Objects.nonNull(form.getExclusiveSessionId())) {
			TExclusiveEntity obj = new TExclusiveEntity();
			// 行lock
			tExclusiveDao.selectForUpdate(obj);
			obj.setExclusiveObjectName(form.getExclusiveObjectName());
			obj.setExclusiveObjectKey(form.getExclusiveObjectKey());
			obj.setExclusiveUser(form.getExclusiveUser());
			obj.setExclusiveSessionId(form.getExclusiveSessionId());
			obj.setExclusiveLimitDate(new Timestamp(System.currentTimeMillis()));
			obj.setInsertUser(form.getUserId());
			obj.setUpdateUser(form.getUserId());
			List<TExclusiveEntity> lockList = tExclusiveDao.selectLock(obj);
			if (Objects.nonNull(lockList) && !lockList.isEmpty()) {
				lockFailedList.add(lockList.get(0));
			} else {
				Timestamp limitDate = calcExclusiveLimitDate();
				obj.setExclusiveLimitDate(limitDate);
				tExclusiveDao.insert(obj);
				lockedList.add(obj);
			}
			//tExclusiveDao.commit();
		}
		result.put(SUCCESS_KEY, lockedList);
		result.put(FAIL_KEY, lockFailedList);
		return result;
	}

	//ロック情報の破棄
	public void releaseLock(ExclusiveForm form) {
		if (Objects.nonNull(form) && Objects.nonNull(form.getExclusiveObjectName())
				&& Objects.nonNull(form.getExclusiveObjectKey()) && Objects.nonNull(form.getExclusiveSessionId())) {
			TExclusiveEntity obj = new TExclusiveEntity();
			// 行lock
			tExclusiveDao.selectForUpdate(obj);
			obj.setExclusiveObjectName(form.getExclusiveObjectName());
			obj.setExclusiveObjectKey(form.getExclusiveObjectKey());
			obj.setExclusiveSessionId(form.getExclusiveSessionId());
			tExclusiveDao.delete(obj);
			//tExclusiveDao.commit();
		}
	}

	//ロック情報の取得
	public Map<String,List<TExclusiveEntity>> getMultiLock(ExclusiveForm form) {
		Map<String,List<TExclusiveEntity>> result = new HashMap<String,List<TExclusiveEntity>>();
		List<TExclusiveEntity> lockedList = new ArrayList<TExclusiveEntity>();
		List<TExclusiveEntity> lockFailedList = new ArrayList<TExclusiveEntity>();

		if (Objects.nonNull(form) && Objects.nonNull(form.getExclusiveObjectName())
				&& Objects.nonNull(form.getExclusiveSessionId())) {
			if (Objects.nonNull(form.getExclusiveObjectKeyList()) && !form.getExclusiveObjectKeyList().isEmpty()) {
				TExclusiveEntity exc = new TExclusiveEntity();
				// lock
				tExclusiveDao.selectForUpdate(exc);
				for (String exclusiveObjectKey : form.getExclusiveObjectKeyList()) {
					TExclusiveEntity obj = new TExclusiveEntity();
					obj.setExclusiveObjectName(form.getExclusiveObjectName());
					obj.setExclusiveObjectKey(exclusiveObjectKey);
					obj.setExclusiveUser(form.getExclusiveUser());
					obj.setExclusiveSessionId(form.getExclusiveSessionId());
					obj.setExclusiveLimitDate(new Timestamp(System.currentTimeMillis()));
					obj.setInsertUser(form.getUserId());
					obj.setUpdateUser(form.getUserId());
					List<TExclusiveEntity> lockList = tExclusiveDao.selectLock(obj);
					if (Objects.nonNull(lockList) && !lockList.isEmpty()) {
						lockFailedList.add(lockList.get(0));
					} else {
						Timestamp limitDate = calcExclusiveLimitDate();
						obj.setExclusiveLimitDate(limitDate);
						tExclusiveDao.insert(obj);
						lockedList.add(obj);
					}
				}
				//tExclusiveDao.commit();
			}
		}
		result.put(SUCCESS_KEY, lockedList);
		result.put(FAIL_KEY, lockFailedList);
		return result;
	}

	//ロック情報の破棄
	public void releaseMultiLock(ExclusiveForm form) {
		if (Objects.nonNull(form) && Objects.nonNull(form.getExclusiveObjectName())
				&& Objects.nonNull(form.getExclusiveObjectKeyList()) && Objects.nonNull(form.getExclusiveSessionId())) {
			TExclusiveEntity obj = new TExclusiveEntity();
			// lock
			tExclusiveDao.selectForUpdate(obj);
			for (String exclusiveObjectKey : form.getExclusiveObjectKeyList()) {
				obj.setExclusiveObjectName(form.getExclusiveObjectName());
				obj.setExclusiveObjectKey(exclusiveObjectKey);
				obj.setExclusiveSessionId(form.getExclusiveSessionId());
				tExclusiveDao.delete(obj);
			}
			//tExclusiveDao.commit();
		}
	}

	private Timestamp calcExclusiveLimitDate() {
		long base = System.currentTimeMillis();
		base += LOCK_LIMIT_TIME;
		return new Timestamp(base);
	}
}
