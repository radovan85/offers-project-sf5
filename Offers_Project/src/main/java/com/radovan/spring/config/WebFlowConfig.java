package com.radovan.spring.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring5.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring5.webflow.view.FlowAjaxThymeleafView;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

	@Autowired
	private LocalValidatorFactoryBean localValidatorFacotryBean;
	
	@Autowired
	private SpringMvcConfiguration springMvcConfiguration;

	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder() //
				.setBasePath("/WEB-INF/templates/flows") //
				.addFlowLocation("/messageFlow/contact-flow.xml", "messageFlow") //
				.setFlowBuilderServices(this.flowBuilderServices()) //
				.build();
	}

	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(this.flowRegistry()) //
				.build();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder() //
				.setViewFactoryCreator(this.mvcViewFactoryCreator()) // Important!
				.setValidator(this.localValidatorFacotryBean).build();
	}
	// ----------------------------------------------------------

	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setOrder(-1);
		handlerMapping.setFlowRegistry(this.flowRegistry());
		return handlerMapping;
	}

	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(this.flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}

	@Bean
	public ViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setViewResolvers(Collections.singletonList(this.thymeleafViewResolver()));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	@Bean
	@Description("Thymeleaf AJAX view resolver for Spring WebFlow")
	public AjaxThymeleafViewResolver thymeleafViewResolver() {
		AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
		viewResolver.setViewClass(FlowAjaxThymeleafView.class);
		viewResolver.setTemplateEngine(this.springMvcConfiguration.templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}

	@Bean 
	public SpringMvcConfiguration getSpringMvcConfiguration() {
		return new SpringMvcConfiguration();
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidatorBean() {
		return new LocalValidatorFactoryBean();
	}
	
	
	
	
}
