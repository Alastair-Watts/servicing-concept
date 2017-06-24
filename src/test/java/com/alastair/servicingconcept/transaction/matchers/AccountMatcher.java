package com.alastair.servicingconcept.transaction.matchers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.hamcrest.CustomMatcher;
import org.hamcrest.Description;

import com.alastair.servicingconcept.transaction.Account;

public class AccountMatcher extends CustomMatcher<Account> {

	@SuppressWarnings("unused")
	private Optional<Integer> id = Optional.empty();
	@SuppressWarnings("unused")
	private Optional<Date> nextActivity = Optional.empty();
	private final List<String> failedFields = new ArrayList<>();

	private AccountMatcher(String description) {
		super(description);
	}

	public static AccountMatcher anAccount() {
		return new AccountMatcher("Account matches");
	}

	public AccountMatcher withId(Integer id) {
		this.id = Optional.of(id);
		return this;
	}

	public AccountMatcher withNextActivityDate(Date nextActivity) {
		this.nextActivity = Optional.of(nextActivity);
		return this;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean matches(Object item) {

		failedFields.addAll(Arrays.asList(this.getClass().getDeclaredFields()).stream()
				.filter(field -> Optional.class.equals(field.getType())).filter(field -> {
					try {
						return ((Optional) field.get(this)).isPresent();
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}).map(field -> {
					try {
						Field fieldOnItem = item.getClass().getDeclaredField(field.getName());
						fieldOnItem.setAccessible(true);
						return new ImmutableTriple<>(field.getName(), fieldOnItem.get(item),
								((Optional) field.get(this)).get());
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				})
				.filter(triple -> !triple.getMiddle().equals(triple.getRight())).map(triple -> triple.getLeft()
						+ " - expected: " + triple.getMiddle() + " but was: " + triple.getRight())
				.collect(Collectors.toList()));

		return failedFields.isEmpty();
	}

	@Override
	public void describeMismatch(Object item, Description mismatchDescription) {
		mismatchDescription.appendValueList("Fields did not match: ", ",", "", failedFields);

	}
}
