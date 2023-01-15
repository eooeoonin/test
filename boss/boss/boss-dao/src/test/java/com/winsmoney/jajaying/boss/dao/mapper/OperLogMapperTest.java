package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.dao.po.OperLogPo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/boss-bean.xml")
public class OperLogMapperTest {

    @Autowired
    private OperLogMapper operLogMapper;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void insert_test() {
        OperLogPo po = new OperLogPo();
        po.setId("22222222222222");
        po.setOperId("111111");
        po.setOperType(OperType.CREATE);
        po.setCode("addUser");
        po.setContent("创建用户");
        po.setInputData("input");
        po.setOutputData("output");
        po.setOperAddress("192.168.1.1");
        po.setOperName("铁皮");
        po.setOperTime(new Date());
        po.setCreateTime(new Date());
        operLogMapper.insert(po);
    }
}