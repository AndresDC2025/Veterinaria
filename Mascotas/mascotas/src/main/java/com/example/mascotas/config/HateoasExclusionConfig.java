package com.example.mascotas.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Excluye los beans de HATEOAS de springdoc que son incompatibles con Spring Boot 4.x.
 * springdoc 2.8.9 intenta usar HateoasProperties que no existe en Spring Boot 4.x.
 */
@Configuration
public class HateoasExclusionConfig implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof BeanDefinitionRegistry registry) {
            String[] problematicBeans = {
                "hateoasHalProvider",
                "collectionModelContentConverter",
                "linksSchemaCustomizer"
            };
            for (String beanName : problematicBeans) {
                if (registry.containsBeanDefinition(beanName)) {
                    registry.removeBeanDefinition(beanName);
                }
            }
        }
    }
}