package com.kakaopay.money.service.mq;

import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DividendRepository;
import com.kakaopay.money.service.DistributionService;
import com.kakaopay.money.service.processor.picker.DividendPicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReceiveDividendRequestReceiver {

	private final DividendPicker dividendPicker;
	private final DistributionService distributionService;
	private final DividendRepository dividendRepository;


	@Autowired
	public ReceiveDividendRequestReceiver(
			@Qualifier("sequentialDividendPicker") DividendPicker dividendPicker,
			DistributionService distributionService, DividendRepository dividendRepository) {
		this.dividendPicker = dividendPicker;
		this.distributionService = distributionService;
		this.dividendRepository = dividendRepository;
	}


	@Transactional
	@JmsListener(destination = ReceiveDividendRequest.DESTINATION_NAME, containerFactory = "defaultJmsFactory")
	public void receiveMessage(ReceiveDividendRequest receiveDividendRequest) {
		Distribution distribution =
				distributionService.getDistributionByToken(receiveDividendRequest.getToken());
		Dividend picked = dividendPicker.pick(distribution);
		picked.setReceiverId(receiveDividendRequest.getReceiverId());
		picked.setDistribution(distribution);

		dividendRepository.save(picked);
	}


}
