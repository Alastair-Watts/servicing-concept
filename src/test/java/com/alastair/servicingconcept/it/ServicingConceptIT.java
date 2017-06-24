package com.alastair.servicingconcept.it;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import com.alastair.servicingconcept.endpoints.Routes;
import com.alastair.servicingconcept.transaction.Account;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServicingConceptIT {

	@LocalServerPort
	private int port;

	private WebClient webClient;

	@Before
	public void setup() {
		webClient = WebClient.create("http://localhost:" + port);
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		webClient.put().uri(Routes.Account.CREATE).accept(MediaType.APPLICATION_JSON_UTF8).exchange();

		Flux<Account> flux = webClient.get().uri(Routes.Account.CREATE).accept(MediaType.APPLICATION_STREAM_JSON)
				.exchange().flatMapMany(response -> response.bodyToFlux(Account.class));

		flux.doOnNext(System.out::println).subscribe();
	}

}
