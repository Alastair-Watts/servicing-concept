package com.alastair.servicingconcept.transaction;

import org.springframework.stereotype.Service;

@Service
public class AccountNumberGenerator {

	private Integer max;

	public AccountNumberGenerator(AccountLookup dao) {
		dao.findAll().collectList().doOnNext(list -> max = list.stream().map(account -> account.getAccountNumber()).sorted()
				.findFirst().orElse(0)).subscribe();
	}

	public Integer getNext() {
		return max++;
	}

}
