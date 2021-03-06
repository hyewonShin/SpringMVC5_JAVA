package kr.co.softcampus.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softcampus.beans.UserBean;
import kr.co.softcampus.dao.UserDao;
import kr.co.softcampus.validator.UserValidator;

@Service
public class UserService {

	//DAO로부터 넘겨부터 값이 NULL인지 판단하여 사용여부를 확인함.
	@Autowired
	private UserDao userDao;
	
	//로그인 성공시 데이터를 담기위해 주입받아준다(로그인한 사용자의 정보가 담겨져있다).
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	
	// 아이디 중복확인 메서드
	public boolean checkUserIdExist(String user_id) {
		
		String user_name = userDao.checkUserIdExist(user_id);
		
		if(user_name == null) {
			return true;
		} else {
			return false;
		}
	} 
	
	//회원가입시 정보를 저장하는 메서드
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	//로그인 성공했을 시 
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		//반환타입 없이 
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempLoginUserBean);	
	
		if(tempLoginUserBean2 != null) { //가져온 데이터가 있다면 로그인 성공
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true); //로그인 성공 여부
		}
	}
	
	//정보수정 데이터 가져오는 메서드
	public void getModifyUserInfo(UserBean modifyUserBean) {
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx());
	
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
	}

	public void modifyUserInfo(UserBean modifyUserBean) {
	
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		
		userDao.modifyUserInfo(modifyUserBean);
	}

}

