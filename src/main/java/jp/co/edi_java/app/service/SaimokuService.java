package jp.co.edi_java.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MSaimokuKousyuDao;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;

@Service
@Scope("request")
public class SaimokuService {

	@Autowired
    public MSaimokuKousyuDao mSaimokuKousyuDao;

	public MSaimokuKousyuEntity get(String saimokuKousyuCode) {
		return mSaimokuKousyuDao.select(saimokuKousyuCode);
	}

	public List<MSaimokuKousyuEntity> getList() {
		return mSaimokuKousyuDao.selectAll();
	}

}
