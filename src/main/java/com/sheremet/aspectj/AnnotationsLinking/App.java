package com.sheremet.aspectj.AnnotationsLinking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App{
	public static void main( String[] args ){
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
		MouseCatDrawer drawer = (MouseCatDrawer) context.getBean("drawer");
		drawer.start();
	}
}