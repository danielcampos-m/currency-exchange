package com.example.microservices.currencyexchangeservice.resources;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.Thread;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservices.currencyexchangeservice.domain.CurrencyExchange;
import com.example.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

import io.micrometer.core.annotation.Timed;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;

@RestController
public class CurrencyExchangeController {
	
//	private final Histogram requestDuration;
//	
//	public CurrencyExchangeController(CollectorRegistry collectorRegistry) {
//		requestDuration = Histogram.build()
//				.name("request_duration")
//				.help("Time for HTTTP request")
//				.register(collectorRegistry);
//	}

	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
//		Histogram.Timer timer = requestDuration.startTimer();
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data");
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
//		timer.observeDuration();
		
		return currencyExchange;
	}
	
	@GetMapping("/up")
	public String up() {
//		Histogram.Timer timer = requestDuration.startTimer();
//		timer.observeDuration();
		return "up";
	}
	
	@GetMapping("/upwait")
	public String upwait() {
//		Histogram.Timer timer = requestDuration.startTimer();
		Integer num = 2000;
		try{
			Thread.sleep(Long.parseLong(num.toString()));
			wait(Long.parseLong(num.toString()));
		}catch(Exception e){
			System.out.println("alou");
		}
		
		
		
//		timer.observeDuration();
		return "up";
	}
}
