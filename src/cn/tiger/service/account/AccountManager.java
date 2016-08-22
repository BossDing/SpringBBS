package cn.tiger.service.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.tiger.dao.account.RoleDao;
import cn.tiger.dao.account.UserDao;
import cn.tiger.dao.account.UserInfoDao;
import cn.tiger.entity.account.AcctRole;
import cn.tiger.entity.account.AcctUser;
import cn.tiger.entity.account.AcctUserInfo;

@Component
//Ĭ�Ͻ����е����к��������������.
@Transactional
public class AccountManager {
	
	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);
	
	private UserDao userDao;
	private RoleDao roleDao;
	private UserInfoDao userInfoDao;
	
	@Transactional(readOnly = true)
	public AcctRole getRole(String name) {
		return roleDao.findUniqueBy("name", name);
	}

	@Transactional(readOnly = true)
	public AcctUser getUser(Long id) {
		return userDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public int getUserRankBySql(Long id) {
		return userDao.getUserRankBySql(id);
	}
	
	public void saveUser(AcctUser entity) {
		userDao.save(entity);
	}
	
	public void saveUserInfo(AcctUserInfo entity) {
		userInfoDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public AcctUser getUserByEmail(String email){
		return userDao.findUniqueBy("email",email);
	}
	
	/**
	 * ɾ���û�,�������ɾ����������Ա���׳��쳣.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("����Ա{}����ɾ����������Ա�û�", SpringSecurityUtils.getCurrentUserName());
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userDao.delete(id);
	}
	
	/**
	 * �ж��Ƿ񳬼�����Ա.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
	/**
	 * ʹ�����Թ���������ѯ�û�.
	 */
	@Transactional(readOnly = true)
	public Page<AcctUser> searchUser(final Page<AcctUser> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}
	
	@Transactional(readOnly = true)
	public AcctUser findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	@Transactional(readOnly = true)
	public AcctUser findUserByUserNameAndEmail(String loginName,String email) {
		return userDao.findUserByUserNameAndEmail(loginName, email);
	}
	
	@Transactional(readOnly = true)
	public AcctUser findUserByUserNameAndPassword(String loginName,String password) {
		return userDao.findUserByUserNameAndPassword(loginName, password);
	}
	
	@Transactional(readOnly = true)
	public AcctUser findUserByLoginNameOrEmail(String loginName_email) {
		AcctUser acctUser=userDao.findUniqueBy("loginName", loginName_email);
		if(acctUser!=null){
			return acctUser;
		}else{
			acctUser=userDao.findUniqueBy("email", loginName_email);
		}
		return userDao.findUniqueBy("email", loginName_email);
	}
	
	//����Id����User
	@Transactional(readOnly = true)
	public AcctUser findUserById(Long id) {
		return userDao.get(id);
	}

	//����Id����UserInfo
	@Transactional(readOnly = true)
	public AcctUserInfo findUserInfoById(Long id) {
		return userInfoDao.get(id);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	@Autowired
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

}
