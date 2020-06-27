package com.kakaopay.money.service.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveDividendRequest {

	public static final String DESTINATION_NAME = "receiveDividendRequestQueue";

	private String token;
	private Long receiverId;

}
