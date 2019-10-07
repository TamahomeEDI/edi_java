package jp.co.edi_java.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MEigyousyoGroupDao;
import jp.co.edi_java.app.dao.MKarekiDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.MSaimokuKousyuDao;
import jp.co.edi_java.app.dao.copy.CopyMEigyousyoDao;
import jp.co.edi_java.app.dao.copy.CopyMEigyousyoGroupDao;
import jp.co.edi_java.app.dao.copy.CopyMGyousyaDao;
import jp.co.edi_java.app.dao.copy.CopyMGyousyaEigyousyoDao;
import jp.co.edi_java.app.dao.copy.CopyMGyousyaSaimokuDao;
import jp.co.edi_java.app.dao.copy.CopyMKarekiDao;
import jp.co.edi_java.app.dao.copy.CopyMKoujiDao;
import jp.co.edi_java.app.dao.copy.CopyMSaimokuKousyuDao;
import jp.co.edi_java.app.dao.copy.CopyMSyainDao;
import jp.co.edi_java.app.dao.copy.CopyMSyainEigyousyoDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaEigyousyoDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaSaimokuDao;
import jp.co.edi_java.app.dao.jtm.VOrderEigyousyoDao;
import jp.co.edi_java.app.dao.jtm.VOrderEigyousyoGroupDao;
import jp.co.edi_java.app.dao.jtm.VOrderGyousyaDao;
import jp.co.edi_java.app.dao.jtm.VOrderGyousyaEigyousyoDao;
import jp.co.edi_java.app.dao.jtm.VOrderGyousyaSaimokuDao;
import jp.co.edi_java.app.dao.jtm.VOrderKarekiDao;
import jp.co.edi_java.app.dao.jtm.VOrderKoujiDao;
import jp.co.edi_java.app.dao.jtm.VOrderSaimokuKousyuDao;
import jp.co.edi_java.app.dao.jtm.VOrderSyainDao;
import jp.co.edi_java.app.dao.jtm.VOrderSyainEigyousyoDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dao.syain.MSyainEigyousyoDao;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MEigyousyoGroupEntity;
import jp.co.edi_java.app.entity.MKarekiEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.copy.CopyMEigyousyoEntity;
import jp.co.edi_java.app.entity.copy.CopyMEigyousyoGroupEntity;
import jp.co.edi_java.app.entity.copy.CopyMGyousyaEigyousyoEntity;
import jp.co.edi_java.app.entity.copy.CopyMGyousyaEntity;
import jp.co.edi_java.app.entity.copy.CopyMGyousyaSaimokuEntity;
import jp.co.edi_java.app.entity.copy.CopyMKarekiEntity;
import jp.co.edi_java.app.entity.copy.CopyMKoujiEntity;
import jp.co.edi_java.app.entity.copy.CopyMSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.copy.CopyMSyainEigyousyoEntity;
import jp.co.edi_java.app.entity.copy.CopyMSyainEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEigyousyoEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaSaimokuEntity;
import jp.co.edi_java.app.entity.jtm.VOrderEigyousyoEntity;
import jp.co.edi_java.app.entity.jtm.VOrderEigyousyoGroupEntity;
import jp.co.edi_java.app.entity.jtm.VOrderGyousyaEigyousyoEntity;
import jp.co.edi_java.app.entity.jtm.VOrderGyousyaEntity;
import jp.co.edi_java.app.entity.jtm.VOrderGyousyaSaimokuEntity;
import jp.co.edi_java.app.entity.jtm.VOrderKarekiEntity;
import jp.co.edi_java.app.entity.jtm.VOrderKoujiEntity;
import jp.co.edi_java.app.entity.jtm.VOrderSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.jtm.VOrderSyainEigyousyoEntity;
import jp.co.edi_java.app.entity.jtm.VOrderSyainEntity;
import jp.co.edi_java.app.entity.syain.MSyainEigyousyoEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;

@Service
@Scope("request")
public class JtmService {

	//支店マスタ
	@Autowired
    public VOrderEigyousyoDao vOrderEigyousyoDao;
	@Autowired
    public MEigyousyoDao mEigyousyoDao;
	@Autowired
    public CopyMEigyousyoDao copyMEigyousyoDao;

	//地区本部マスタ
	@Autowired
    public VOrderEigyousyoGroupDao vOrderEigyousyoGroupDao;
	@Autowired
    public MEigyousyoGroupDao mEigyousyoGroupDao;
	@Autowired
    public CopyMEigyousyoGroupDao copyMEigyousyoGroupDao;

	//業者マスタ
	@Autowired
    public VOrderGyousyaDao vOrderGyousyaDao;
	@Autowired
    public MGyousyaDao mGyousyaDao;
	@Autowired
    public CopyMGyousyaDao copyMGyousyaDao;

	//業者支店マスタ
	@Autowired
    public VOrderGyousyaEigyousyoDao vOrderGyousyaEigyousyoDao;
	@Autowired
    public MGyousyaEigyousyoDao mGyousyaEigyousyoDao;
	@Autowired
    public CopyMGyousyaEigyousyoDao copyMGyousyaEigyousyoDao;

	//業者細目マスタ
	@Autowired
    public VOrderGyousyaSaimokuDao vOrderGyousyaSaimokuDao;
	@Autowired
    public MGyousyaSaimokuDao mGyousyaSaimokuDao;
	@Autowired
    public CopyMGyousyaSaimokuDao copyMGyousyaSaimokuDao;

	//工事マスタ
	@Autowired
    public VOrderKoujiDao vOrderKoujiDao;
	@Autowired
    public MKoujiDao mKoujiDao;
	@Autowired
    public CopyMKoujiDao copyMKoujiDao;

	//細目工種マスタ
	@Autowired
    public VOrderSaimokuKousyuDao vOrderSaimokuKousyuDao;
	@Autowired
    public MSaimokuKousyuDao mSaimokuKousyuDao;
	@Autowired
    public CopyMSaimokuKousyuDao copyMSaimokuKousyuDao;

	//社員マスタ
	@Autowired
    public VOrderSyainDao vOrderSyainDao;
	@Autowired
    public MSyainDao mSyainDao;
	@Autowired
    public CopyMSyainDao copyMSyainDao;

	//社員支店マスタ
	@Autowired
    public VOrderSyainEigyousyoDao vOrderSyainEigyousyoDao;
	@Autowired
    public MSyainEigyousyoDao mSyainEigyousyoDao;
	@Autowired
    public CopyMSyainEigyousyoDao copyMSyainEigyousyoDao;

	//家歴マスタ
	@Autowired
    public VOrderKarekiDao vOrderKarekiDao;
	@Autowired
    public MKarekiDao mKarekiDao;
	@Autowired
    public CopyMKarekiDao copyMKarekiDao;


	/** 支店マスタ */
	public void backupEigyousyo() {
		//コピーテーブルの削除
		copyMEigyousyoDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMEigyousyoDao.insertAll();
	}

	public void truncateEigyousyo() {
		//マスタテーブルの削除
		mEigyousyoDao.deleteAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertEigyousyoAll3() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;

		int totalCount = vOrderEigyousyoDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderEigyousyoEntity> list = vOrderEigyousyoDao.selectPaging(from, to);
			for (VOrderEigyousyoEntity vEntity : list) {
				MEigyousyoEntity entity = new MEigyousyoEntity();
				BeanUtils.copyBeanToBean(vEntity, entity);
				mEigyousyoDao.insert(entity);
				insertCount++;
			}
		}
		countMap.put("totalCount", totalCount);
		countMap.put("insertCount", insertCount);
		return countMap;
	}

	//100件ずつの更新
	public Map<String, Object> insertEigyousyoAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderKoujiDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderEigyousyoEntity> list = vOrderEigyousyoDao.selectPaging(from, to);
			for (VOrderEigyousyoEntity vEntity : list) {
				CopyMEigyousyoEntity copyEntity = copyMEigyousyoDao.select(vEntity.getEigyousyoCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MEigyousyoEntity entity = new MEigyousyoEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mEigyousyoDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mEigyousyoDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertEigyousyoAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderEigyousyoEntity> list = vOrderEigyousyoDao.selectAll();
		for (VOrderEigyousyoEntity vOrderEntity : list) {
			CopyMEigyousyoEntity copyEntity = copyMEigyousyoDao.select(vOrderEntity.getEigyousyoCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MEigyousyoEntity entity = new MEigyousyoEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mEigyousyoDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mEigyousyoDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 地区本部マスタ */
	public void backupEigyousyoGroup() {
		//コピーテーブルの削除
		copyMEigyousyoGroupDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMEigyousyoGroupDao.insertAll();
	}

	public void truncateEigyousyoGroup() {
		//マスタテーブルの削除
		mEigyousyoGroupDao.deleteAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertEigyousyoGroupAll3() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;

		int totalCount = vOrderEigyousyoGroupDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderEigyousyoGroupEntity> list = vOrderEigyousyoGroupDao.selectPaging(from, to);
			for (VOrderEigyousyoGroupEntity vEntity : list) {
				MEigyousyoGroupEntity entity = new MEigyousyoGroupEntity();
				BeanUtils.copyBeanToBean(vEntity, entity);
				mEigyousyoGroupDao.insert(entity);
				insertCount++;
			}
		}
		countMap.put("totalCount", totalCount);
		countMap.put("insertCount", insertCount);
		return countMap;
	}

	//100件ずつの更新
	public Map<String, Object> insertEigyousyoGroupAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderEigyousyoGroupDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderEigyousyoGroupEntity> list = vOrderEigyousyoGroupDao.selectPaging(from, to);
			for (VOrderEigyousyoGroupEntity vEntity : list) {
				CopyMEigyousyoGroupEntity copyEntity = copyMEigyousyoGroupDao.select(vEntity.getEigyousyoGroupCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MEigyousyoGroupEntity entity = new MEigyousyoGroupEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mEigyousyoGroupDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mEigyousyoGroupDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertEigyousyoGroupAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderEigyousyoGroupEntity> list = vOrderEigyousyoGroupDao.selectAll();
		for (VOrderEigyousyoGroupEntity vOrderEntity : list) {
			CopyMEigyousyoGroupEntity copyEntity = copyMEigyousyoGroupDao.select(vOrderEntity.getEigyousyoGroupCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MEigyousyoGroupEntity entity = new MEigyousyoGroupEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mEigyousyoGroupDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mEigyousyoGroupDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 業者マスタ */
	public void backupGyousya() {
		//コピーテーブルの削除
		copyMGyousyaDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMGyousyaDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertGyousyaAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderGyousyaDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderGyousyaEntity> list = vOrderGyousyaDao.selectPaging(from, to);
			for (VOrderGyousyaEntity vEntity : list) {
				CopyMGyousyaEntity copyEntity = copyMGyousyaDao.select(vEntity.getGyousyaCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MGyousyaEntity entity = new MGyousyaEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					String gyousyaName = vEntity.getGyousyaName();
					gyousyaName = gyousyaName.replace("㈱", "(株)");
					gyousyaName = gyousyaName.replace("㈲", "(有)");
					if(copyEntity == null) {
						mGyousyaDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mGyousyaDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertGyousyaAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderGyousyaEntity> list = vOrderGyousyaDao.selectAll();
		for (VOrderGyousyaEntity vOrderEntity : list) {
			CopyMGyousyaEntity copyEntity = copyMGyousyaDao.select(vOrderEntity.getGyousyaCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MGyousyaEntity entity = new MGyousyaEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				String gyousyaName = vOrderEntity.getGyousyaName();
				gyousyaName = gyousyaName.replace("㈱", "(株)");
				gyousyaName = gyousyaName.replace("㈲", "(有)");
				entity.setGyousyaName(gyousyaName);
				if(copyEntity == null) {
					mGyousyaDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mGyousyaDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 業者支店マスタ */
	public void backupGyousyaEigyousyo() {
		//コピーテーブルの削除
		copyMGyousyaEigyousyoDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMGyousyaEigyousyoDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertGyousyaEigyousyoAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 10000;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderGyousyaEigyousyoDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderGyousyaEigyousyoEntity> list = vOrderGyousyaEigyousyoDao.selectPaging(from, to);
			for (VOrderGyousyaEigyousyoEntity vEntity : list) {
				CopyMGyousyaEigyousyoEntity copyEntity = copyMGyousyaEigyousyoDao.select(vEntity.getGyousyaCode(), vEntity.getEigyousyoCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MGyousyaEigyousyoEntity entity = new MGyousyaEigyousyoEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mGyousyaEigyousyoDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mGyousyaEigyousyoDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertGyousyaEigyousyoAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderGyousyaEigyousyoEntity> list = vOrderGyousyaEigyousyoDao.selectAll();
		for (VOrderGyousyaEigyousyoEntity vOrderEntity : list) {
			CopyMGyousyaEigyousyoEntity copyEntity = copyMGyousyaEigyousyoDao.select(vOrderEntity.getGyousyaCode(), vOrderEntity.getEigyousyoCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MGyousyaEigyousyoEntity entity = new MGyousyaEigyousyoEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mGyousyaEigyousyoDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mGyousyaEigyousyoDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 業者細目マスタ */
	public void backupGyousyaSaimoku() {
		//コピーテーブルの削除
		copyMGyousyaSaimokuDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMGyousyaSaimokuDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertGyousyaSaimokuAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 10000;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderGyousyaSaimokuDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderGyousyaSaimokuEntity> list = vOrderGyousyaSaimokuDao.selectPaging(from, to);
			for (VOrderGyousyaSaimokuEntity vEntity : list) {
				CopyMGyousyaSaimokuEntity copyEntity = copyMGyousyaSaimokuDao.select(vEntity.getGyousyaCode(), vEntity.getSaimokuKousyuCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MGyousyaSaimokuEntity entity = new MGyousyaSaimokuEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mGyousyaSaimokuDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mGyousyaSaimokuDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertGyousyaSaimokuAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderGyousyaSaimokuEntity> list = vOrderGyousyaSaimokuDao.selectAll();
		for (VOrderGyousyaSaimokuEntity vOrderEntity : list) {
			CopyMGyousyaSaimokuEntity copyEntity = copyMGyousyaSaimokuDao.select(vOrderEntity.getGyousyaCode(), vOrderEntity.getSaimokuKousyuCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MGyousyaSaimokuEntity entity = new MGyousyaSaimokuEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mGyousyaSaimokuDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mGyousyaSaimokuDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 工事マスタ */
	public void backupKouji() {
		//コピーテーブルの削除
		copyMKoujiDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMKoujiDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertKoujiAll() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 10000;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderKoujiDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderKoujiEntity> list = vOrderKoujiDao.selectPaging(from, to);
			for (VOrderKoujiEntity vEntity : list) {
				CopyMKoujiEntity copyEntity = copyMKoujiDao.select(vEntity.getKoujiCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MKoujiEntity entity = new MKoujiEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mKoujiDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mKoujiDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertKoujiAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;

		List<VOrderKoujiEntity> list = vOrderKoujiDao.selectAll();
		for (VOrderKoujiEntity vOrderEntity : list) {
			CopyMKoujiEntity copyEntity = copyMKoujiDao.select(vOrderEntity.getKoujiCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MKoujiEntity entity = new MKoujiEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mKoujiDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mKoujiDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 細目工種マスタ */
	public void backupSaimokuKousyu() {
		//コピーテーブルの削除
		copyMSaimokuKousyuDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMSaimokuKousyuDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertSaimokuKousyuAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderSaimokuKousyuDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderSaimokuKousyuEntity> list = vOrderSaimokuKousyuDao.selectPaging(from, to);
			for (VOrderSaimokuKousyuEntity vEntity : list) {
				CopyMSaimokuKousyuEntity copyEntity = copyMSaimokuKousyuDao.select(vEntity.getSaimokuKousyuCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MSaimokuKousyuEntity entity = new MSaimokuKousyuEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mSaimokuKousyuDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mSaimokuKousyuDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertSaimokuKousyuAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderSaimokuKousyuEntity> list = vOrderSaimokuKousyuDao.selectAll();
		for (VOrderSaimokuKousyuEntity vOrderEntity : list) {
			CopyMSaimokuKousyuEntity copyEntity = copyMSaimokuKousyuDao.select(vOrderEntity.getSaimokuKousyuCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MSaimokuKousyuEntity entity = new MSaimokuKousyuEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mSaimokuKousyuDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mSaimokuKousyuDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

	/** 社員マスタ */
	public void backupSyain() {
		//コピーテーブルの削除
		copyMSyainDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMSyainDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertSyainAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderSyainDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderSyainEntity> list = vOrderSyainDao.selectPaging(from, to);
			for (VOrderSyainEntity vEntity : list) {
				CopyMSyainEntity copyEntity = copyMSyainDao.select(vEntity.getSyainCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MSyainEntity entity = new MSyainEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					// 退職者レコードは新規取り込みはしない
					if(copyEntity == null && vEntity.getTaisyokuFlg() != 1) {
						mSyainDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mSyainDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertSyainAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderSyainEntity> list = vOrderSyainDao.selectAll();
		for (VOrderSyainEntity vOrderEntity : list) {
			CopyMSyainEntity copyEntity = copyMSyainDao.select(vOrderEntity.getSyainCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MSyainEntity entity = new MSyainEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mSyainDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mSyainDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}


	/** 社員支店マスタ */
	public void backupSyainEigyousyo() {
		//コピーテーブルの削除
		copyMSyainEigyousyoDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMSyainEigyousyoDao.insertAll();
	}
	public void truncateSyainEigyousyo() {
		//マスタテーブルの削除
		mSyainEigyousyoDao.deleteAll();
	}

	//1000件ずつの更新
	public Map<String, Object> insertSyainEigyousyoAll3() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 1000;
		int insertCount = 0;

		int totalCount = vOrderSyainEigyousyoDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderSyainEigyousyoEntity> list = vOrderSyainEigyousyoDao.selectPaging(from, to);
			for (VOrderSyainEigyousyoEntity vEntity : list) {
				MSyainEigyousyoEntity entity = new MSyainEigyousyoEntity();
				BeanUtils.copyBeanToBean(vEntity, entity);
				mSyainEigyousyoDao.insert(entity);
				insertCount++;
			}
		}
		countMap.put("totalCount", totalCount);
		countMap.put("insertCount", insertCount);
		return countMap;
	}

	//100件ずつの更新
	public Map<String, Object> insertSyainEigyousyoAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderSyainEigyousyoDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderSyainEigyousyoEntity> list = vOrderSyainEigyousyoDao.selectPaging(from, to);
			for (VOrderSyainEigyousyoEntity vEntity : list) {
				CopyMSyainEigyousyoEntity copyEntity = copyMSyainEigyousyoDao.select(vEntity.getSyainCode(), vEntity.getEigyousyoCode(), vEntity.getEigyousyoGroupCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MSyainEigyousyoEntity entity = new MSyainEigyousyoEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mSyainEigyousyoDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mSyainEigyousyoDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertSyainEigyousyoAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderSyainEigyousyoEntity> list = vOrderSyainEigyousyoDao.selectAll();
		for (VOrderSyainEigyousyoEntity vOrderEntity : list) {
			CopyMSyainEigyousyoEntity copyEntity = copyMSyainEigyousyoDao.select(vOrderEntity.getSyainCode(), vOrderEntity.getEigyousyoCode(), vOrderEntity.getEigyousyoGroupCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MSyainEigyousyoEntity entity = new MSyainEigyousyoEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mSyainEigyousyoDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mSyainEigyousyoDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}


	/** 家歴マスタ */
	public void backupKareki() {
		//コピーテーブルの削除
		copyMKarekiDao.deleteAll();
		//マスタテーブルからコピーテーブルへコピー
		copyMKarekiDao.insertAll();
	}

	//100件ずつの更新
	public Map<String, Object> insertKarekiAll2() {
		Map<String, Object> countMap = new HashMap<>();
		int chkCount = 100;
		int insertCount = 0;
		int updateCount = 0;

		int totalCount = vOrderKarekiDao.count();
		double forCount = Math.ceil((double)totalCount / chkCount);
		for (int i = 0; i < forCount; i++) {
			int from = (i * chkCount) + 1;
			int to = (i + 1) * chkCount;
			List<VOrderKarekiEntity> list = vOrderKarekiDao.selectPaging(from, to);
			for (VOrderKarekiEntity vEntity : list) {
				CopyMKarekiEntity copyEntity = copyMKarekiDao.select(vEntity.getKarekiBoxCode());
				//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
				if(copyEntity == null || (vEntity.getSaisyuuKousinDate() != null && (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())))){
					MKarekiEntity entity = new MKarekiEntity();
					BeanUtils.copyBeanToBean(vEntity, entity);
					if(copyEntity == null) {
						mKarekiDao.insert(entity);
						insertCount++;
					}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vEntity.getSaisyuuKousinDate())) {
						mKarekiDao.update(entity);
						updateCount++;
					}
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		countMap.put("totalCount", totalCount);
		return countMap;
	}

	public Map<String, Object> insertKarekiAll() {
		Map<String, Object> countMap = new HashMap<>();
		int insertCount = 0;
		int updateCount = 0;
		List<VOrderKarekiEntity> list = vOrderKarekiDao.selectAll();
		for (VOrderKarekiEntity vOrderEntity : list) {
			CopyMKarekiEntity copyEntity = copyMKarekiDao.select(vOrderEntity.getKarekiBoxCode());
			//差分更新（データが存在しない場合はinsert、最終更新日が変更されていたらupdate）
			if(copyEntity == null || (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate()))){
				MKarekiEntity entity = new MKarekiEntity();
				BeanUtils.copyBeanToBean(vOrderEntity, entity);
				if(copyEntity == null) {
					mKarekiDao.insert(entity);
					insertCount++;
				}else if (copyEntity != null && !copyEntity.getSaisyuuKousinDate().equals(vOrderEntity.getSaisyuuKousinDate())) {
					mKarekiDao.update(entity);
					updateCount++;
				}
			}
		}
		countMap.put("insertCount", insertCount);
		countMap.put("updateCount", updateCount);
		return countMap;
	}

}
