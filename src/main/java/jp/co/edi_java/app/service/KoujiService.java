package jp.co.edi_java.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.MProjectTypeDao;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.MProjectTypeEntity;

@Service
@Scope("request")
public class KoujiService {

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MProjectTypeDao mProjectTypeDao;

	public MKoujiEntity get(String koujiCode) {
		return mKoujiDao.select(koujiCode);
	}

	public List<MKoujiEntity> getList(String eigyousyoCode, String syainCode, String kanseiKbn, String projectTypeCode) {
		return mKoujiDao.selectList(eigyousyoCode, syainCode, kanseiKbn, projectTypeCode);
	}

	public List<MProjectTypeEntity> getProjectType() {
		return mProjectTypeDao.selectAll();
	}

}
