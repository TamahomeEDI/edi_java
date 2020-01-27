package jp.co.edi_java.app.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MEigyousyoGroupDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao;
import jp.co.edi_java.app.dao.syain.MSyainEigyousyoDao;
import jp.co.edi_java.app.dto.GyousyaEigyousyoDto;
import jp.co.edi_java.app.dto.SyainEigyousyoDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MEigyousyoGroupEntity;
import jp.co.edi_java.app.form.EigyousyoForm;
import jp.co.edi_java.app.util.crypto.CipherUtils;

@Service
@Scope("request")
public class EigyousyoService {

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MEigyousyoGroupDao mEigyousyoGroupDao;

	@Autowired
    public MSyainEigyousyoDao mSyainEigyousyoDao;

	@Autowired
    public MGyousyaEigyousyoDao mGyousyaEigyousyoDao;

	public MEigyousyoEntity get(String eigyousyoCode) {
		return mEigyousyoDao.select(eigyousyoCode);
	}

	public List<SyainEigyousyoDto> getListByUser(String syainCode) {
		return mSyainEigyousyoDao.selectList(syainCode);
	}

	public List<SyainEigyousyoDto> getListByUserHeadOffice(String syainCode) {
		return mSyainEigyousyoDao.selectListByHeadOffice(syainCode);
	}

	public List<SyainEigyousyoDto> getListByUserHeadOfficeKoumu(String syainCode) {
		return mSyainEigyousyoDao.selectListByHeadOfficeKoumu(syainCode);
	}

//	public List<MEigyousyoEntity> getListByPartner(String gyousyaCode) {
//		List<MGyousyaEigyousyoEntity> list = mGyousyaEigyousyoDao.selectList(gyousyaCode);
//		List<String> eigyousyoList = new ArrayList<>();
//		for (MGyousyaEigyousyoEntity eigyousyo : list) {
//			eigyousyoList.add(eigyousyo.eigyousyoCode);
//		}
//		return mEigyousyoDao.selectList(eigyousyoList);
//	}

	public List<GyousyaEigyousyoDto> getListByPartner(String gyousyaCode, String eigyousyoGroupCode) {
		return mEigyousyoDao.selectListByPartner(gyousyaCode, eigyousyoGroupCode);
	}

	public List<MEigyousyoEntity> getListByGroup(String eigyousyoGroupCode) {
		return mEigyousyoDao.selectListByGroup(eigyousyoGroupCode);
	}

	public MEigyousyoGroupEntity getGroup(String eigyousyoGroupCode) {
		return mEigyousyoGroupDao.select(eigyousyoGroupCode);
	}

	public List<MEigyousyoGroupEntity> getGroupList() {
		return mEigyousyoGroupDao.selectAll();
	}

	public List<MEigyousyoGroupEntity> getGroupListByUser(String syainCode) {
		List<String> groupCodeList = mSyainEigyousyoDao.selectGroupCodeList(syainCode);
		return mEigyousyoGroupDao.selectList(groupCodeList);
	}

	public List<MEigyousyoGroupEntity> getGroupListByPartner(String gyousyaCode) {
		return mGyousyaEigyousyoDao.selectGroupList(gyousyaCode);
	}

	//支店コードのデコード
	public String decodeEigyousyoCode(EigyousyoForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptEigyousyoCode())) {
			ret = CipherUtils.getDecryptAES(form.getEncryptEigyousyoCode());
		}
		return ret;
	}
}
