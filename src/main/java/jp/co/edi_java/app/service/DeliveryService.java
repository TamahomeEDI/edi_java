package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TDeliveryItemDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.dto.DeliveryDto;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.keepalive.springbootfw.util.consts.CommonConsts;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;

@Service
@Scope("request")
public class DeliveryService {

	@Autowired
    public MailService mailService;

	@Autowired
    public TDeliveryDao tDeliveryDao;

	@Autowired
    public TDeliveryItemDao tDeliveryItemDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public TGyousyaMailaddressDao tGyousyaMailaddressDao;

	public TDeliveryEntity get(String deliveryNumber) {
		return tDeliveryDao.select(deliveryNumber);
	}

	public TDeliveryEntity getByOrderNumber(String orderNumber, int deliveryCount) {
		return tDeliveryDao.selectByOrderNumber(orderNumber, deliveryCount);
	}

	public List<TDeliveryItemEntity> getItemList(String deliveryNumber) {
		return tDeliveryItemDao.selectAll(deliveryNumber);
	}

	public List<DeliveryDto> getList(String orderNumber, String remandFlg) {
		List<DeliveryDto> dtoList = new ArrayList<>();
		List<TDeliveryEntity> list = tDeliveryDao.selectAll(orderNumber, remandFlg);
		for (TDeliveryEntity delivery : list) {
			DeliveryDto dto = new DeliveryDto();
			List<TDeliveryItemEntity> itemList = getItemList(delivery.getDeliveryNumber());
			BeanUtils.copyBeanToBean(delivery, dto);
			dto.setItemList(itemList);
			dtoList.add(dto);
		}
		return dtoList;
	}

	public String regist(DeliveryForm form) {
		//納品情報登録
		String deliveryNumber = registDelivery(form);
		//納品明細登録
		registDeliveryItem(deliveryNumber, form.itemList, form.userId);
		return deliveryNumber;
	}

	//納品情報登録
	private String registDelivery(DeliveryForm form) {
		String deliveryNumber = "D_" + System.currentTimeMillis();
		TDeliveryEntity entity = new TDeliveryEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setDeliveryNumber(deliveryNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tDeliveryDao.insert(entity);
		return deliveryNumber;
	}

	//納品明細登録
	private void registDeliveryItem(String deliveryNumber, List<TDeliveryItemEntity> itemList, String userId) {
		int count = 1;
		for (TDeliveryItemEntity item : itemList) {
			TDeliveryItemEntity entity = new TDeliveryItemEntity();
			BeanUtils.copyBeanToBean(item, entity);
			entity.setDeliveryNumber(deliveryNumber);
			entity.setItemNumber(count);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			tDeliveryItemDao.insert(entity);
			count++;
		}
	}

	public int update(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
//		BeanUtils.copyBeanToBean(form, entity);
		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setFileId(form.getFileId());
		entity.setUpdateUser(form.getUserId());
		return tDeliveryDao.update(entity);
	}

	public int delete(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
		return tDeliveryDao.delete(entity);
	}

	public void approval(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				String userKbn = form.getUserKbn();
				String judgmentKbn = dto.getJudgmentKbn();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				//承認の場合
				if(judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_APPROVAL)) {
					//工務担当
					if(userKbn.equals(CommonConsts.USER_KBN_STAFF)) {
						entity.setStaffReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setStaffReceiptDate(timestamp);
					}
					//工務事務
					else if (userKbn.equals(CommonConsts.USER_KBN_CLERK)) {
						entity.setClerkReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setClerkReceiptDate(timestamp);
					}
					//工務課長
					else if (userKbn.equals(CommonConsts.USER_KBN_MANAGER)) {
						entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
						entity.setManagerReceiptDate(timestamp);
					}
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.update(entity);
				}
				//差し戻しの場合
				else if (judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_REMAND)) {

					entity.setRemandFlg(CommonConsts.REMAND_FLG_ON);
					entity.setRemandDate(timestamp);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.update(entity);

					//差し戻し用のメール送信
					TDeliveryEntity delivery = this.get(dto.getWorkNumber());
					String gyousyaCode = delivery.getGyousyaCode();
					List<String> addressList = tGyousyaMailaddressDao.selectList(gyousyaCode);
					MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
					mailService.sendMailDeliveryRemand(addressList, gyousya, delivery);
				}

				/*
				 * 初期開発ではSAP連携をしない
				//SAP連携（工務課長で承認した場合）
				if(judgmentKbn.equals(CommonConsts.JUDGMENT_KBN_APPROVAL) && userKbn.equals(CommonConsts.USER_KBN_MANAGER)) {

				}
				*/
			}
		}
	}

}
