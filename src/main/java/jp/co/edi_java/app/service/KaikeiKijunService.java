package jp.co.edi_java.app.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.TKaikeiKijunDao;
import jp.co.edi_java.app.entity.TKaikeiKijunEntity;
import jp.co.edi_java.app.form.KaikeiKijunForm;

@Service
@Scope("request")
public class KaikeiKijunService {

	@Autowired
    public MailService mailService;

	@Autowired
    public TKaikeiKijunDao tKaikeiKijunDao;

	private static String FORM_DIR = "/partner/completionbasis?h=";

	private static String BASE_URL;

	private KaikeiKijunService(@Value("${edi.base.url}") String url) {
		BASE_URL = url;
	}

	public TKaikeiKijunEntity get(String gyousyaCode) {
		return tKaikeiKijunDao.select(gyousyaCode);
	}

	public TKaikeiKijunEntity getByHashCode(String hashCode) {
		return tKaikeiKijunDao.selectByHashCode(hashCode);
	}

	public List<TKaikeiKijunEntity> getAll() {
		return tKaikeiKijunDao.selectAll();
	}

	public List<String> convertToHash(List<TKaikeiKijunEntity> kaikeiKijunList) {
		List<String> list = new ArrayList<>();
		for (TKaikeiKijunEntity kaikeiKijun : kaikeiKijunList) {
			String sha1 = DigestUtils.sha1Hex(kaikeiKijun.getGyousyaCode().getBytes());
//			String base64En = base64Encode(kaikeiKijun.getGyousyaCode().getBytes());
			String url = BASE_URL + FORM_DIR + sha1;
			kaikeiKijun.setUrl(url);
			kaikeiKijun.setHashCode(sha1);
			tKaikeiKijunDao.update(kaikeiKijun);
			list.add(sha1);
		}
		return list;
	}

	public void sendMail(String gyousyaCode) {
		TKaikeiKijunEntity kaikeiKijun = get(gyousyaCode);
		mailService.sendMailKaikeiKijun(kaikeiKijun.getMailaddress(), kaikeiKijun.getGyousyaName(), kaikeiKijun.getStaffName(), kaikeiKijun.getKaikeiKijun(), kaikeiKijun.getRegistDate());
	}

	public String decodeHashCode(String hashCode) {
		String deocde = null;
		try {
			deocde = base64Decode(hashCode);
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return deocde;
	}

	/**
	 * Base64エンコードを行う.
	 *
	 * @param bytes Base64エンコードを施す対象データ
	 * @return Base64エンコード処理後の文字列
	 */
	public static String base64Encode(byte[] bytes) {
	  return (new Base64()).encodeToString(bytes);
	}

	/**
	 * Base64デコードを行う.
	 *
	 * @param encoded Base64エンコード後の文字列.
	 * @return デコード後の文字列.
	 * @throws UnsupportedEncodingException 不正な文字エンコードを指定した場合にthrowされる.
	 */
	public static String base64Decode(String encoded) throws UnsupportedEncodingException {
	  byte[] buff = (new Base64()).decode(encoded);
	  return new String(buff, "utf-8");
	}

	public int update(KaikeiKijunForm form) {
		TKaikeiKijunEntity kaikeiKijun = get(form.getGyousyaCode());
		kaikeiKijun.setKaikeiKijun(form.getKaikeiKijun());
		kaikeiKijun.setRegistDate(form.getRegistDate());
		kaikeiKijun.setStaffName(form.getStaffName());
		return tKaikeiKijunDao.update(kaikeiKijun);
	}

}
