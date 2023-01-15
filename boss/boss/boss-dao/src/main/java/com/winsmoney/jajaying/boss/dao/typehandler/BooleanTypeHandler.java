/**
 * Project:boss
 * File:BooleanTypeHandler.java
 * Date:2016-03-31
 * Copyright (c) 2016, CodeCreator@jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.SMALLINT) 
@MappedTypes(value=Boolean.class)
public class BooleanTypeHandler implements TypeHandler<Boolean> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setBoolean(i, parameter);
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		int str = rs.getInt(columnName);    
        Boolean rt = Boolean.FALSE;
        if (str!=0){    
          rt = Boolean.TRUE;    
      }    
        return rt; 
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		Boolean b = rs.getBoolean(columnIndex);    
        return b;    
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return cs.getBoolean(columnIndex);
	}

}
