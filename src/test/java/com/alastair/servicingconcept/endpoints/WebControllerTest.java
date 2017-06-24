package com.alastair.servicingconcept.endpoints;

import static com.alastair.servicingconcept.transaction.matchers.AccountMatcher.anAccount;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Subscriber;

import com.alastair.servicingconcept.transaction.Account;
import com.alastair.servicingconcept.transaction.AccountLookup;

import reactor.core.publisher.Flux;

@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest {

	@Mock
	private AccountLookup dao;

	@InjectMocks
	private WebController controller;

	@Test
	public void addAccount_CallsDao() {
		controller.addAccount();

		ArgumentCaptor<Account> account = ArgumentCaptor.forClass(Account.class);
		verify(dao, Mockito.times(1)).save(account.capture());
		assertThat(account.getValue(), anAccount().withNextActivityDate(LocalDate.now().toDate()));
	}

	@Test
	public void getAccountsWithActivities_ReturnsAccountsFromDao() {
		Flux<Account> accounts = new LocalFlux();
		when(dao.findByNextActivityBefore(LocalDate.now().plusDays(1).toDate())).thenReturn(accounts);

		Flux<Account> accountsWithActivities = controller.getAccountsWithActivities();

		assertEquals(accounts, accountsWithActivities);
	}

	private class LocalFlux extends Flux<Account> {

		@Override
		public void subscribe(final Subscriber<? super Account> s) {
			// TODO Auto-generated method stub

		}
	};
}
