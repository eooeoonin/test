package com.winsmoney.jajaying.boss.domain.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.LogUtil;

public class PropertiesFactoryUtil {
	
	private static Hashtable<String, Properties> register = new Hashtable<String, Properties>();
	
	
//	   @Value("${pic}")
//	    private String pic ;
//
//	    public String getPic() {
//	        return pic;
//	    }
//
//	    public void setPic(String pic) {
//	        this.pic = pic;
//	    }
//	    
//	    
//	    
//	    @Value("${html}")
//	    private String html ;
//
//		public String getHtml() {
//			return html;
//		}
//
//		public void setHtml(String html) {
//			this.html = html;
//		}
//	    	
//		
//		 @Value("${excel}")
//		 private String excel;
//
//		public String getExcel() {
//			return excel;
//		}
//
//		public void setExcel(String excel) {
//			this.excel = excel;
//		}
		
		
		public static Properties getProperties(String propertiesName){
			//String propertiesPath = PropertiesUtil.getConfigPath(propertiesName);
			//return PropertiesUtil.createProperties(propertiesPath);
			
			InputStream is = null;
			Properties p = null;
			try {
				p = (Properties) register.get(propertiesName);
				if(p == null){
					is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName);
					p = new Properties();
					p.load(is);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return p;
			
		}
	
		 
}
