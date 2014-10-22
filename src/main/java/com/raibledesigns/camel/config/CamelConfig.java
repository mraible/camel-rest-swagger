package com.raibledesigns.camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.raibledesigns.camel")
public class CamelConfig extends CamelConfiguration {
	@Value("${logging.trace.enabled}")
	private Boolean tracingEnabled;

	@Override
	protected void setupCamelContext(CamelContext camelContext) throws Exception {
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:application.properties");
		camelContext.addComponent("properties", pc);
		// see if trace logging is turned on
		if (tracingEnabled) {
			camelContext.setTracing(true);
		}

		// enable performance metrics: http://www.davsclaus.com/2014/09/more-metrics-in-apache-camel-214.html
		camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());

		super.setupCamelContext(camelContext);
	}

	@Bean
	public Tracer camelTracer() {
		Tracer tracer = new Tracer();
		tracer.setTraceExceptions(false);
		tracer.setTraceInterceptors(true);
		tracer.setLogName("com.raibledesigns.com.api.trace");
		return tracer;
	}
}
