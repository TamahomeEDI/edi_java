package jp.co.edi_java.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.entity.syain.MSyainEntity;

@Service
@Scope("request")
public class UserService {

	@Autowired
    public MSyainDao mSyainDao;

	public MSyainEntity get(String syainCode) {
		return mSyainDao.select(syainCode);
	}

	public List<MSyainEntity> getListByEigyousyoList(List<String> eigyousyoCodeList) {
		return mSyainDao.selectListByEigyousyoList(eigyousyoCodeList);
	}

	public List<MSyainEntity> getListByEigyousyo(String eigyousyoCode) {
		return mSyainDao.selectListByEigyousyo(eigyousyoCode);
	}

	/** 990000ユーザの代替に工務課長を取得。主にメール送信用 */
	public List<MSyainEntity> getListBySyokusyu3(String eigyousyoCode) {
		return mSyainDao.selectListBySyokusyu3(eigyousyoCode);
	}

}
