package com.winsmoney.jajaying.boss.domain;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;
import com.winsmoney.platform.framework.redis.map.MapManager;
import com.winsmoney.platform.framework.redis.support.ICache;
import com.winsmoney.platform.framework.web.session.DistributedSessionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/boss-bean.xml")
public class BaseTest {
	@Autowired
	private MenuDomain menuDomain;
	@Resource(name = "mapManager")
	protected MapManager mapManager ; 

	@Autowired
	protected ICache<String> redisMagager;
	
	/**
	 * menu列表
	 */
	@Test
	public void testMenu(){
		List<Menu> listMenu = menuDomain.listMenu(new Menu());
		System.out.println(listMenu);
	}
	
	/**
	 * menu列表
	 */
	@Test
	public void findMenusByRoleId(){
		List<MenuBo> roles = menuDomain.findMenusByRoleId("1");
		System.out.println(JSONObject.toJSONString(roles));
	}
	
	@Test
	public void testRedis(){
		String object = mapManager.get("296ae781-921d-4bdd-87cb-ce57a6254b9f", String.class);
		System.out.println(object);
		DistributedSessionData distributedSessionData = redisMagager.get("", DistributedSessionData.class);
		System.out.println(distributedSessionData);
	}
	
}
