package com.springinaction.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;

import java.util.List;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private List<ViewResolver> viewResolvers;

    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry())
                //.addFlowExecutionListener(new SecurityFlowExecutionListener(), "*")
                .build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices())
                .setBasePath("classpath*:/flows")
                .addFlowLocationPattern("/**/*-flow.xml")
                .build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator())
                .setDevelopmentMode(true)
                .build();
    }

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {

        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(viewResolvers);
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }

}