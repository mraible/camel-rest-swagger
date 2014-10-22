package com.raibledesigns.camel.hello;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "hello")
public class HelloWorld {
	private String message;
}