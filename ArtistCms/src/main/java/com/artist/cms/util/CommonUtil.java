package com.artist.cms.util;


import com.artist.cms.domain.Coop;
import com.artist.cms.domain.TuserInfo;
import com.artist.cms.domain.UserInf;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 * @author Administrator
 *
 */
public class CommonUtil {

	/**
	 * 从TuserInfos获得注册用户的userNo列表
	 * @param tuserInfos
	 * @return
	 */
	public static List<String> getInRegisterUserNoListFromTuserInfos(List<TuserInfo> tuserInfos) {
		List<String> registerUserNoList = new ArrayList<String>();
		Integer size = tuserInfos.size();
		Integer len = size/1000;
		if (size%1000==0) { //能被1000整除
			getRegisterUserNoListFromTuserInfos(registerUserNoList, len, tuserInfos);
		} else { //不能被1000整除
			getRegisterUserNoListFromTuserInfos(registerUserNoList, len, tuserInfos);
			int start = len*1000;
			int end = len*1000+size%1000-1;
			registerUserNoList.add(getInRegisterUserNoStringBufferFromTuserInfos(start, end, tuserInfos));
		}
		return registerUserNoList;
	}
	
	/**
	 * 从TuserInfos获得RegisterUserNo列表
	 * @param registerUserNoList
	 * @param len
	 * @param tuserInfos
	 */
	public static void getRegisterUserNoListFromTuserInfos(List<String> registerUserNoList, int len, List<TuserInfo> tuserInfos) {
		for (int i = 0; i < len; i++) {
			int start = i*1000;
			int end = i*1000+999;
			registerUserNoList.add(getInRegisterUserNoStringBufferFromTuserInfos(start, end, tuserInfos));
		}
	}
	
	/**
	 * 获得注册用户userNo集合String
	 * @param start
	 * @param end
	 * @param tuserInfos
	 * @return
	 */
	public static String getInRegisterUserNoStringBufferFromTuserInfos(int start, int end, List<TuserInfo> tuserInfos) {
		StringBuffer buffer = new StringBuffer("");
		for (int j = start; j <= end; j++) {
			if (j == end) {
				buffer.append("'"+tuserInfos.get(j).getUserno()+"'");
			} else {
				buffer.append("'"+tuserInfos.get(j).getUserno()+"',");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获得客户端所有渠道号的列表
	 * @param coops
	 * @return
	 */
	public static List<String> getInCoopIdList(List<Coop> coops) {
		List<String> coopIdList = new ArrayList<String>();
		Integer size = coops.size();
		Integer len = size/1000;
		if (size%1000==0) { //能被1000整除
			getCoopIdList(coopIdList, len, coops);
		} else { //不能被1000整除
			getCoopIdList(coopIdList, len, coops);
			int start = len*1000;
			int end = len*1000+size%1000-1;
			coopIdList.add(getInCoopIdStringBuffer(start, end, coops));
		}
		return coopIdList;
	}
	
	/**
	 * 获得CoopId列表
	 * @param coopIdList
	 * @param len
	 * @param coops
	 */
	public static void getCoopIdList(List<String> coopIdList, int len, List<Coop> coops) {
		for (int i = 0; i < len; i++) {
			int start = i*1000;
			int end = i*1000+999;
			coopIdList.add(getInCoopIdStringBuffer(start, end, coops));
		}
	}
	
	/**
	 * 获得渠道的集合String
	 * @param start
	 * @param end
	 * @param coops
	 * @return
	 */
	public static String getInCoopIdStringBuffer(int start, int end, List<Coop> coops) {
		StringBuffer buffer = new StringBuffer("");
		for (int j = start; j <= end; j++) {
			if (j == end) {
				buffer.append("'"+coops.get(j).getCoopid()+"'");
			} else {
				buffer.append("'"+coops.get(j).getCoopid()+"',");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 从TuserInfos获得注册用户的userNo列表
	 * @param userInfs
	 * @return
	 */
	public static List<String> getInRegisterUserNoListFromUserInfs(List<UserInf> userInfs) {
		List<String> registerUserNoList = new ArrayList<String>();
		Integer size = userInfs.size();
		Integer len = size/1000;
		if (size%1000==0) { //能被1000整除
			getRegisterUserNoListFromUserInfs(registerUserNoList, len, userInfs);
		} else { //不能被1000整除
			getRegisterUserNoListFromUserInfs(registerUserNoList, len, userInfs);
			int start = len*1000;
			int end = len*1000+size%1000-1;
			registerUserNoList.add(getInRegisterUserNoStringBufferFromUserInfs(start, end, userInfs));
		}
		return registerUserNoList;
	}
	
	/**
	 * 从TuserInfos获得RegisterUserNo列表
	 * @param registerUserNoList
	 * @param len
	 * @param userInfs
	 */
	public static void getRegisterUserNoListFromUserInfs(List<String> registerUserNoList, int len, List<UserInf> userInfs) {
		for (int i = 0; i < len; i++) {
			int start = i*1000;
			int end = i*1000+999;
			registerUserNoList.add(getInRegisterUserNoStringBufferFromUserInfs(start, end, userInfs));
		}
	}
	
	/**
	 * 获得注册用户userNo集合String
	 * @param start
	 * @param end
	 * @param userInfs
	 * @return
	 */
	public static String getInRegisterUserNoStringBufferFromUserInfs(int start, int end, List<UserInf> userInfs) {
		StringBuffer buffer = new StringBuffer("");
		for (int j = start; j <= end; j++) {
			if (j == end) {
				buffer.append("'"+userInfs.get(j).getUserno()+"'");
			} else {
				buffer.append("'"+userInfs.get(j).getUserno()+"',");
			}
		}
		return buffer.toString();
	}

}
