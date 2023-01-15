package com.winsmoney.jajaying.boss.controller.user;

import com.winsmoney.platform.framework.core.util.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResForm{
	private Money redMoneyBalance;
	private Money balance;
	private Money freezeAmount;
}
