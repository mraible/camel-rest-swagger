package com.raibledesigns.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * Base class for routes. Contains global exception handler.
 */
public abstract class AbstractRouteBuilder extends RouteBuilder {

	@Value("${camel.env}")
	private String camelEnv;

	@Value("${camel.alert.email}")
	private String camelAlertEmail;

	/**
	 * Default exception handling for all routes.
	 *
	 * @throws Exception
	 */
	@Override
	public void configure() throws Exception {
		onException(Exception.class)
				.setHeader("routeId", property(Exchange.FAILURE_ROUTE_ID))
				.setHeader("endpoint", property(Exchange.FAILURE_ENDPOINT))
				.setHeader("exception", property(Exchange.EXCEPTION_CAUGHT))
				.setHeader("subject", simple("Camel Error (" + camelEnv + ") - ${exception.class.simpleName}"))
				.transform(simple("${exception.message}\n\nStacktrace Details:\n\n${exception.stacktrace}"))
				.to("freemarker:/templates/mail/error.ftl")
				.to("smtp://{{mail.host}}?contentType=text/plain&to=" + camelAlertEmail +
						"&from={{mail.from}}&subject=${headers.subject})");
	}
}
