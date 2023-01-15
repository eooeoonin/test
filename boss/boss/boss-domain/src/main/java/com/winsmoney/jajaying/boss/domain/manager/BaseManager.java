/**
 * Project:boss
 * File:BaseManager.java
 * Date:2016-03-31
 * Copyright (c) 2016, CodeCreator@jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.manager;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.platform.framework.core.model.BaseModel;
import com.winsmoney.platform.framework.core.model.BasePo;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;
import com.winsmoney.framework.pagehelper.PageHelper;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by guoxuechong on 2016/4/1.
 */
public abstract class BaseManager<T extends BaseModel,M extends BaseMapper<P>,P extends BasePo>
{
    /**
     * 可写的sqlSession
     */
    @Autowired
    @Qualifier("sqlSessionMaster")
    protected SqlSession sqlSessionMaster;
    /**
     * 只读的sqlSession
     */
    @Autowired
    @Qualifier("sqlSessionSlave")
    protected SqlSession sqlSessionSlave;

    public void setSqlSessionMaster(SqlSession sqlSessionMaster) {
        this.sqlSessionMaster = sqlSessionMaster;
    }

    public void setSqlSessionSlave(SqlSession sqlSessionSlave) {
        this.sqlSessionSlave = sqlSessionSlave;
    }

    /**
     * 取得uuid的服务
     */
    @Autowired(required=true)
    protected SequenceGenerator sequenceGenerator;

    /**
     * Bo的class
     */
    protected Class<T> resultClass;

    /**
     * mapper的class
     */
    protected Class<M> mapperClass;

    /**
     * Po的class
     */
    protected Class<P> poClass;



    public BaseManager()
    {
        resultClass= (Class<T>) ((ParameterizedType) super.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        mapperClass = (Class<M>) ((ParameterizedType) super.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];

        poClass = (Class<P>) ((ParameterizedType) super.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[2];
    }

    protected M getMasterMapper()
    {
        return sqlSessionMaster.getMapper(mapperClass);
    }

    protected M getSlaveMapper()
    {
        return sqlSessionSlave.getMapper(mapperClass);
    }

    //基础方法//////////////////////////////////////////

    /**
     * 根据id取得对象
     * @param id
     * @return
     */
    public T getById(String id)
    {
        if(id == null || id.isEmpty())
        {
            throw new IllegalArgumentException("id is null");
        }
        T result;
        try
        {
            P data = this.getSlaveMapper().getById(id);
            result = BeanMapper.map(data,resultClass);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 根据对象
     * @param condition
     * @return
     */
    public T get(T condition)
    {
        if (null == condition) {
            throw new IllegalArgumentException("condition is null");
        }
        T result;
        try
        {
            P param=BeanMapper.map(condition,poClass);
            P data = this.getSlaveMapper().get(param);
            result = BeanMapper.map(data,resultClass);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 根据添加统计
     * @param condition
     * @return
     */
    public int count(T condition)
    {
        if(null == condition)
        {
            throw new IllegalArgumentException("condition is null");
        }
        int result;
        try
        {
            P param=BeanMapper.map(condition,poClass);
            result = this.getSlaveMapper().count(param);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 根据对象取得列表记录
     * @param condition
     * @return
     */
    public List<T> list(T condition)
    {
        if(null == condition)
        {
            throw new IllegalArgumentException("condition is null");
        }
        List<T> result;
        try
        {
            P param=BeanMapper.map(condition,poClass);
            List<P> list = this.getSlaveMapper().list(param);
            result = BeanMapper.mapList(list,resultClass);
        }
        catch (Exception e)
        {	
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 分页列表
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<T> list(T condition, int pageNum, int pageSize)
    {

        if(null == condition)
        {
            throw new IllegalArgumentException("condition is null");
        }
        PageInfo<T> pageList;
        try
        {
            P param=BeanMapper.map(condition,poClass);
            PageHelper.startPage(pageNum, pageSize,true);
            List<P> list = this.getSlaveMapper().list(param);
            List<T> resultList = BeanMapper.mapList(list,resultClass);
            pageList = new PageInfo(list);
            pageList.setList(resultList);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return pageList;
    }

    /**
     * 插入新记录
     * @param value
     * @return
     */
    public int insert(T value)
    {
        if(value == null)
        {
            throw new IllegalArgumentException("value is null");
        }
        int result;
        try
        {
            if(StringUtils.isEmpty(value.getId()))  //添加id
            {
//                String systemKey=resultClass.getSimpleName();
                String newId=sequenceGenerator.getUUID();
                value.setId(newId);
            }
            value.setDeleted(false); //默认值
            P data=BeanMapper.map(value,poClass);
            result = this.getMasterMapper().insert(data);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 批量插入
     * @param listObject
     * @return
     */
     public List<T> insertList(List<T> listObject)
     {
         if(listObject == null || listObject.isEmpty())
         {
             throw new IllegalArgumentException("value is null");
         }
         int result;
         try
         {
             List<P> list = new ArrayList<>();
             for(T t: listObject)
             {
                 if(StringUtils.isEmpty(t.getId()))  //添加id
                 {
                     String newId=sequenceGenerator.getUUID();
                     t.setId(newId);
                 }
                 t.setDeleted(false); //默认值
                 P data=BeanMapper.map(t,poClass);
                 list.add( data );
             }
             result = this.getMasterMapper().insertList( list );
             if( result == 0)
                 return null;
             return listObject;
         }
         catch (Exception e)
         {
             throw new RuntimeException(e);
         }
     }
    
    /**
     * 更新记录
     * @param value
     * @return
     */
    public int update(T value)
    {
        if(value == null)
        {
            throw new IllegalArgumentException("value is null");
        }
        int result;
        try
        {
            P data=BeanMapper.map(value,poClass);
            result = this.getMasterMapper().update(data);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;

    }

    /**
     * 根据id删除记录（逻辑删除）
     * @param id
     * @return
     */
    public int delete(String id)
    {
        if(id == null || id.isEmpty())
        {
            throw new IllegalArgumentException("id is null");
        }
        int result;
        try
        {
            result = this.getMasterMapper().delete(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }


}









