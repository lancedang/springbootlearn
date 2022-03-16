package com.lance.learn.redisdemo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class BeanFactoryUtil implements ApplicationContextAware, DisposableBean {
	private static ApplicationContext ctx;
	private static BeanFactoryUtil singleton = new BeanFactoryUtil();

	private BeanFactoryUtil() {
	}

	public static BeanFactoryUtil getInstance() {
		return singleton;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	public void destroy() throws Exception {
		ctx = null;
	}

	public static <T> T getBean(String name) {
		return (T) ctx.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return clazz.cast(BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, clazz));
	}

	public static <T> T getBeanRefactor(Class<T> clazz) {
		return ctx.getBean(clazz);
	}
}
