package com.alastair.servicingconcept.transaction;

import java.util.Date;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface AccountLookup extends ReactiveCrudRepository<Account, Integer> {

	Flux<Account> findByNextActivityBefore(Date nextActivity);
}
