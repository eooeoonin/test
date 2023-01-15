package com.winsmoney.jajaying.boss.dao.typehandler;

import com.winsmoney.jajaying.boss.dao.enums.OperType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 操作类型转换
 *
 * @author howard he
 * @create 2018/7/10 10:04
 */
@MappedJdbcTypes({JdbcType.TINYINT})
@MappedTypes({OperType.class})
public class OperTypeTypeHandler implements TypeHandler<OperType> {

    @Override
    public void setParameter(PreparedStatement ps, int i, OperType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getType());
    }

    @Override
    public OperType getResult(ResultSet rs, String columnName) throws SQLException {
        return OperType.convert(rs.getInt(columnName));
    }

    @Override
    public OperType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return OperType.convert(rs.getInt(columnIndex));
    }

    @Override
    public OperType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return OperType.convert(cs.getInt(columnIndex));
    }
}
