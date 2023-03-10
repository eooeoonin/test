package com.winsmoney.jajaying.boss.baidu.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.winsmoney.jajaying.boss.baidu.ueditor.define.ActionMap;
import com.winsmoney.jajaying.boss.baidu.ueditor.define.AppInfo;
import com.winsmoney.jajaying.boss.baidu.ueditor.define.BaseState;
import com.winsmoney.jajaying.boss.baidu.ueditor.define.State;
import com.winsmoney.jajaying.boss.baidu.ueditor.hunter.FileManager;
import com.winsmoney.jajaying.boss.baidu.ueditor.hunter.ImageHunter;
import com.winsmoney.jajaying.boss.baidu.ueditor.upload.Uploader;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


public class ActionEnter {
	private  static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActionEnter.class);
	private HttpServletRequest request = null;
	
	private String rootPath = null;
	private String contextPath = null;
	
	private String actionType = null;
	
	private ConfigManager configManager = null;

	public ActionEnter ( HttpServletRequest request, String rootPath ) {
		
		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter( "action" );
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance( this.rootPath, this.contextPath, request.getRequestURI().replace(request.getContextPath(),"") );
		
	}
	
	public String exec () {
		
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}

	}
	
	public String invoke() {
		
		if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
			return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
		}
		
		if ( this.configManager == null || !this.configManager.valid() ) {
			return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
		}
		
		State state;
		
		int actionCode = ActionMap.getType( this.actionType );
		
		Map<String, Object> conf = null;
		
		switch ( actionCode ) {
		
			case ActionMap.CONFIG:
				return this.configManager.getAllConfig().toString();
				
			case ActionMap.UPLOAD_IMAGE:
			case ActionMap.UPLOAD_SCRAWL:
			case ActionMap.UPLOAD_VIDEO:
			case ActionMap.UPLOAD_FILE:
			try {
				conf = this.configManager.getConfig( actionCode );
			} catch (JSONException e) {
				logger.error("", e);
			}
				state = new Uploader( request, conf ).doExec();
				break;
				
			case ActionMap.CATCH_IMAGE:
			try {
				conf = configManager.getConfig( actionCode );
			} catch (JSONException e) {
				logger.error("", e);
			}
				String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
				state = new ImageHunter( conf ).capture( list );
				break;
				
			case ActionMap.LIST_IMAGE:
			case ActionMap.LIST_FILE:
			try {
				conf = configManager.getConfig( actionCode );
			} catch (JSONException e) {
				logger.error("", e);
			}
				int start = this.getStartIndex();
				state = new FileManager( conf ).listFile( start );
				break;
			default : return this.configManager.getAllConfig().toString();
		}
		
		return state.toJSONString();
		
	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			logger.error("", e);
			return 0;
		}
		
	}
	
	/**
	 * callback????????????
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
}