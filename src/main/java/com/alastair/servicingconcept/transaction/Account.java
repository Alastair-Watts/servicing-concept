package com.alastair.servicingconcept.transaction;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Document
public class Account {

	private @Id ObjectId id;
	private @NonNull Date nextActivity;
	private @NonNull Integer AccountNumber;

}
