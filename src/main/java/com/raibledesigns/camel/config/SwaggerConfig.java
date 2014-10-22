package com.raibledesigns.camel.config;

import org.apache.camel.component.swagger.spring.SpringRestSwaggerApiDeclarationServlet;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SwaggerConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
	}

	/**
	 * Swagger Camel Configuration
	 */
	@Bean
	public ServletRegistrationBean swaggerServlet() {
		ServletRegistrationBean swagger = new ServletRegistrationBean(new SpringRestSwaggerApiDeclarationServlet(), "/api-docs/*");
		Map<String, String> params = new HashMap<>();
		params.put("base.path", "http://localhost:8080/api");
		params.put("api.title", propertyResolver.getProperty("title"));
		params.put("api.description", propertyResolver.getProperty("description"));
		params.put("api.termsOfServiceUrl", propertyResolver.getProperty("termsOfServiceUrl"));
		params.put("api.license", propertyResolver.getProperty("license"));
		params.put("api.licenseUrl", propertyResolver.getProperty("licenseUrl"));
		swagger.setInitParameters(params);
		return swagger;
	}
}