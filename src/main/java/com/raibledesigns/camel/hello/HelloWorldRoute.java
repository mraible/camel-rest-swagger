package com.raibledesigns.camel.hello;

import com.raibledesigns.camel.AbstractRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldRoute extends AbstractRouteBuilder {
	@Override
	public void configure() throws Exception {
		super.configure();

		// servlet is configured in Application.java
		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

		rest("/say")
				.get("/hello").outType(HelloWorld.class)
				.to("direct:talk");
		from("direct:talk")
				.process(exchange -> {
					HelloWorld hw = new HelloWorld();
					hw.setMessage("Howdy!");
					exchange.getIn().setBody(hw);
				});
	}
}
