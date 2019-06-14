package jp.co.edi_java.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MTextDao;
import jp.co.edi_java.app.entity.MTextEntity;

@Service
@Scope("request")
public class TextService {

	@Autowired
    public MTextDao mTextDao;

	public List<MTextEntity> getList(String formType) {
		return mTextDao.selectList(formType);
	}


}
