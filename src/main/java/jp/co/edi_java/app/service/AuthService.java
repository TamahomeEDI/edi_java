package jp.co.edi_java.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;

@Service
@Scope("request")
public class AuthService {

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public TGyousyaAccountDao tGyosyaAccountDao;

	@Autowired
    public MGyousyaDao mGyosyaDao;

	//タマホームのログイン
	public MSyainEntity userLogin(String syainCode, String password) {
		return mSyainDao.selectByLogin(syainCode, password);
	}

	//業者のログイン
	public TGyousyaAccountEntity partnerLogin(String gyousyaCode, String password) {
		return tGyosyaAccountDao.selectByLogin(gyousyaCode, password);
	}

//	//業者のログイン
//	public MGyousyaEntity partnerLogin(String gyousyaCode, String password) {
//		TGyousyaAccountEntity gyousyaAccount = tGyosyaAccountDao.selectByLogin(gyousyaCode, password);
//		return mGyosyaDao.select(gyousyaAccount.getGyousyaCode());
//	}


}
