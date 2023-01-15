package com.winsmoney.jajaying.boss.controller.crowdfunding;

import com.winsmoney.jajaying.crowdfunding.service.response.EstateProjectResVo;
import java.util.Date;

/**
 * Created by wei on 2016/8/20.
 */
public class EstateProjectResForm extends EstateProjectResVo {

    private String companyName;
    
    private String editFlag;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}


    
}
