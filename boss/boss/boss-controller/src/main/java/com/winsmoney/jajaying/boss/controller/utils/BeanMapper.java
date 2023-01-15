package com.winsmoney.jajaying.boss.controller.utils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.google.common.collect.Lists;

/**
 * Java Bean 工具类
 *
 * @author vincent
 *
 */
public class BeanMapper {
	
	private static DozerBeanMapper  dozer = new DozerBeanMapper();

	   public static <T> List<T> mapList2(Collection sourceList, Class<T> destinationClass)
	    {
	        List destinationList = Lists.newArrayList();
	        for (Iterator i$ = sourceList.iterator(); i$.hasNext(); ) { Object sourceObject = i$.next();
	            Object destinationObject = dozer.map(sourceObject, destinationClass);
	            destinationList.add(destinationObject);
	        }
	        return destinationList;
	    }
}
