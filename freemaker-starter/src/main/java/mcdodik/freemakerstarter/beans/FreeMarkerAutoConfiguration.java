package mcdodik.freemakerstarter.beans;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@AutoConfigureAfter(FreeMarkerAutoConfiguration.class)
public class FreeMarkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FreeMarkerConfigurationFactoryBean.class)
    public FreeMarkerConfigurationFactoryBean freemarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        factoryBean.setTemplateLoaderPath("classpath:/templates/");
        return factoryBean;
    }

    @Bean
    @ConditionalOnMissingBean(freemarker.template.Configuration.class)
    public freemarker.template.Configuration freemarkerConfig(FreeMarkerConfigurationFactoryBean factoryBean) {
        return factoryBean.getObject();
    }

}