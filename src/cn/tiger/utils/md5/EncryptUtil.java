package cn.tiger.utils.md5;

import org.apache.commons.codec.digest.DigestUtils;

//useless
public class EncryptUtil {
	/*public static String encryptUsernameAndPassword(String username,String password){
		return DigestUtils.md5Hex(username+password);
	}*/
	
	//�����¼�������Ӽ���
	public static String SEED="seed";
	
	public static String encryptUsernameAndPassword(String password){
		return DigestUtils.md5Hex(SEED+password);
	}
}
