package com.mdhskv.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mdhskv.md.mediapp.bean.MediComSubscriptionToContext;
import com.mdhskv.md.mediapp.bean.SubscribeMediComSericeRq;
import com.mdhskv.md.mediapp.bean.SubscribeMediComSericeRs;

@RestController
@RequestMapping("/activate/{version}")
public class ActivationController{

	@Autowired
	ConversionService conversionService;
	
	private static Logger logger = LoggerFactory.getLogger(ActivationController.class);
	@RequestMapping(method = RequestMethod.POST, value = "/subscribeMediComService")
	public SubscribeMediComSericeRs subscribeWalletService(SubscribeMediComSericeRq req) throws Exception {
		SubscribeMediComSericeRs response = new SubscribeMediComSericeRs();
		MediComSubscriptionToContext context = conversionService.convert(req, MediComSubscriptionToContext.class);
		MediComSubscriptionToContext context1 = conversionService.convert(response, MediComSubscriptionToContext.class);
		System.out.println("seecodnd service"+ context1);
		System.out.println("after conversion" + context);
		return response;
	}
}