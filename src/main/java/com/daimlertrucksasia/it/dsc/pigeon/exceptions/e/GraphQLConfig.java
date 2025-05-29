package com.daimlertrucksasia.it.dsc.pigeon.exceptions.e;

import graphql.GraphQL;
import io.leangen.graphql.spqr.spring.autoconfigure.BaseAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BaseAutoConfiguration.class)
public class GraphQLConfig  {

    @Autowired
    private CustomGraphQLExceptionHandler exceptionHandler;

    @Bean
    public BeanPostProcessor graphQLBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                if (bean instanceof GraphQL && "graphQL".equals(beanName)) {
                    return GraphQL.newGraphQL(((GraphQL) bean).getGraphQLSchema())
                            .defaultDataFetcherExceptionHandler(exceptionHandler)
                            .build();
                }
                return bean;
            }
        };
    }
}
