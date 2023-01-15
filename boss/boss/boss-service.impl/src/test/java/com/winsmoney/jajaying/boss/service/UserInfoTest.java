package com.winsmoney.jajaying.boss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGSelectQueryBlock.ForClause;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.job.TriggerSendSmsJob;
import com.winsmoney.jajaying.msgcenter.service.enums.MessageType;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.ReceiverReqVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.enums.AuthState;
import com.winsmoney.jajaying.user.service.enums.CertType;
import com.winsmoney.jajaying.user.service.enums.UserState;
import com.winsmoney.jajaying.user.service.enums.UserType;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.util.Gender;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

/**
 * Created by ChenKai on 2016/12/9.
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class UserInfoTest {
	private static ExecutorService pool = new ThreadPoolExecutor(30, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

	@Autowired
	private IUserInfoService userInfoService;
	// @Autowired
	// private TriggerSendSmsJob triggerSendSmsJob;

	/**
	 * 取得uuid的服务
	 */
	@Autowired(required = true)
	protected SequenceGenerator sequenceGenerator;

	@Test
	public void geteUserInfo() {
//		UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
//		userInfoReqVo.setUserType(UserType.PERSON);
//		Integer pageSize = 1000;
//		UserCommonResult<Integer> userCountResult = userInfoService.countUserInfo(userInfoReqVo);
//		if (userCountResult.isSuccess()) {
//			Integer pages = (userCountResult.getBusinessObject() / pageSize) < 1 ? 1 : userCountResult.getBusinessObject() / pageSize + 1;
//			UserInfoReqVo userInfoReqVo2 = new UserInfoReqVo();
//			for (int i = 1; i <= pages; i++) {
//				UserCommonResult<PageInfo<UserInfoResVo>> listUserInfo = userInfoService.listUserInfo(userInfoReqVo2, i, pageSize);
//				if (listUserInfo.isSuccess()) {
//					PageInfo<UserInfoResVo> userInfoPage = listUserInfo.getBusinessObject();
//					if (null != userInfoPage.getList() && !userInfoPage.getList().isEmpty()) {
//						sendMsg(userInfoPage, i);
//						final MessageReqVo userMobiles = constructMobile(userInfoPage.getList());
//						pool.execute(new Runnable() {
//							@Override
//							public void run() {
//								sendMsg(userMobiles);
//							}
//						});
//					}
//				}
//			}
//		}
	}

	private void sendMsg(MessageReqVo userMobiles) {
		System.out.println(">>>开始发送");
	}

	private MessageReqVo constructMobile(List<UserInfoResVo> list) {
		MessageReqVo messageReqVo = new MessageReqVo();
		messageReqVo.setContent("短信内容");
		messageReqVo.setMerchant("P2P");
		messageReqVo.setType(MessageType.SMS);
		List<ReceiverReqVo> receiveres = new ArrayList<ReceiverReqVo>();
		for (UserInfoResVo userInfoResVo : list) {
			ReceiverReqVo receiverReqVo = new ReceiverReqVo();
			receiverReqVo.setCellphone(userInfoResVo.getRegisterMobile());
			receiveres.add(receiverReqVo);
		}
		messageReqVo.setReceiveres(receiveres);
		return messageReqVo;
	}

	private void sendMsg(PageInfo<UserInfoResVo> userInfoPage, int i) {
		// constructMobile()
		List<UserInfoResVo> userList = userInfoPage.getList();
		for (UserInfoResVo userInfoResvo : userList) {
			String userId = userInfoResvo.getId();
			System.out.println(">>>>userId=" + userId);
			String mobile = userInfoResvo.getRegisterMobile();

		}
		System.out.println("开始发送短信" + System.currentTimeMillis() + "=> " + i);
	}

	@Test
	public void testTrigger() {
		// triggerSendSmsJob.trigger("好好好");
	}

	@Test
	public void testUserInfoShardMulti() {
		String origin = "0|1|2|3|4|5|6|7|8|9|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z";
		final int pageSize = 1000;
		String[] split = origin.split("\\|");
		for (final String s : split) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
					userInfoReqVo.setUserType(UserType.PERSON);
					userInfoReqVo.setId(s);
					UserCommonResult<PageInfo<UserInfoResVo>> listUserInfoByShard;
					try {
						listUserInfoByShard = userInfoService.listUserInfoByShard(userInfoReqVo, 1, pageSize);
						System.out.println(">>>>>s=" + s + "   " + listUserInfoByShard);
					} catch (Exception e) {
						System.out.println(">>>>>>>>s=" + s);
						e.printStackTrace();
					}

				}
			});
		}
		while (true) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testUserInfoShardOneThread() {
		String origin = "0|1|2|3|4|5|6|7|8|9|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z";
		int pageSize = 1000;
		String[] split = origin.split("\\|");
		for (final String s : split) {
			UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
			userInfoReqVo.setUserType(UserType.PERSON);
			userInfoReqVo.setId(s);
			UserCommonResult<PageInfo<UserInfoResVo>> listUserInfoByShard = userInfoService.listUserInfoByShard(userInfoReqVo, 1, pageSize);
			System.out.println(listUserInfoByShard);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		while (true) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testUserInsert() {

		final int pageSize = 10000;
		int totals = 500000;
		int pages = (totals / pageSize) < 1 ? 1 : totals / pageSize;
		for (int i = 1; i <= pages; i++) {
			final int m = i;
			pool.execute(new Runnable() {
				@Override
				public void run() {
					int start = (m - 1) * pageSize;
					int end = m * pageSize;
					for (int j = start; j < end; j++) {
						UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
						String uuid = sequenceGenerator.getUUID();
						userInfoReqVo.setId(uuid);
						userInfoReqVo.setUserType(UserType.PERSON);
						userInfoReqVo.setGroup("ALL");
						userInfoReqVo.setRegisterMobile("124" + StringUtils.leftPad(String.valueOf(j), 8, "0"));
						userInfoReqVo.setRealName("DBIMPORT");
						userInfoReqVo.setGender(Gender.MAN);
						userInfoReqVo.setCertType(CertType.ID);
						userInfoReqVo.setCertNo("620103199412345678");
//						userInfoReqVo.setAuthState(AuthState.BANKCARD);
						userInfoReqVo.setUserState(UserState.NORMAL);
						userInfoReqVo.setGrade(0);
						userInfoReqVo.setValidateGrade(0);
						userInfoReqVo.setInviteCode("AAAAAA");
						userInfoReqVo.setFrom("ANDROID");
						userInfoReqVo.setChannel("jjy000002");
						System.out.println(">>>>" + j);
						try {
							// userInfoService.insertUserInfo(userInfoReqVo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			});
		}

		while (true) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
