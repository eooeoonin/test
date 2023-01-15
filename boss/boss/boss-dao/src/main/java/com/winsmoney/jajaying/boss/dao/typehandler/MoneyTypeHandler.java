/**
 * Project:boss
 * File:MoneyTypeHandler.java
 * Date:2016-03-31
 * Copyright (c) 2016, CodeCreator@jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.typehandler;

import com.winsmoney.platform.framework.core.util.Money;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014-3-4 下午7:39:17  by 冯为（fengwei3@ucfgroup.com）创建
 */
@SuppressWarnings("rawtypes")
public class MoneyTypeHandler implements TypeHandler {

	/**
	 * Description: 
	 * @Version1.0 2014-3-4 下午7:40:04 by 冯为（fengwei3@ucfgroup.com）创建
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object getResult(ResultSet arg0, String arg1) throws SQLException {
		Money money = new Money();
		money.setCent(arg0.getLong( arg1 ));
		return money; 
	}

	/**
	 * Description: 
	 * @Version1.0 2014-3-4 下午7:40:09 by 冯为（fengwei3@ucfgroup.com）创建
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		Money money = new Money();
		money.setCent(arg0.getLong( arg1 ));
		return money; 
	}

	/**
	 * Description:
	 * @Version1.0 2014-3-4 下午7:40:18 by 冯为（fengwei3@ucfgroup.com）创建
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		Money money = new Money();
		money.setCent(arg0.getLong( arg1 ));
		return money;
	}


	/**
	 * Description: 
	 * @Version1.0 2014-3-4 下午7:40:14 by 冯为（fengwei3@ucfgroup.com）创建
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @throws SQLException
	 */
	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
			JdbcType arg3) throws SQLException {
		if(null != arg2){
			Money money = (Money) arg2;
//			arg0.setBigDecimal(arg1, new BigDecimal( money.getCent()));
			arg0.setLong(arg1,money.getCent());
		}else{
			arg0.setBigDecimal(arg1, null);
		}
	}


}

