package com.alastair.servicingconcept.endpoints;

import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alastair.servicingconcept.transaction.Account;
import com.alastair.servicingconcept.transaction.AccountLookup;
import com.alastair.servicingconcept.transaction.AccountNumberGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebController {

	private final AccountLookup dao;
	private AccountNumberGenerator generator;

	public WebController(AccountLookup dao, AccountNumberGenerator generator) {
		this.dao = dao;
		this.generator = generator;
	}

	@PutMapping(path = Routes.Account.CREATE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<? extends Object> addAccount(
	// @RequestBody AccountCreator accountCreator
	) {
		Account account = new Account(LocalDate.now().toDate(), generator.getNext());
		return dao.save(account).map(createdAccount -> createdAccount.getAccountNumber());
	}

	@GetMapping(path = Routes.Account.CREATE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Account> getAccountsWithActivities() {
		return dao.findByNextActivityBefore(LocalDate.now().plusDays(1).toDate());
	}
}
