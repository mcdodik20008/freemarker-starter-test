package mcdodik.freemakerlib.beans;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@AutoConfigureAfter(FreeMarkerAutoConfiguration.class)
@ConditionalOnProperty(prefix = "freemarker.custom", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FreeMarkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FreeMarkerConfigurationFactoryBean.class)
    public FreeMarkerConfigurationFactoryBean freemarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        factoryBean.setTemplateLoaderPath("classpath:/templates/");
        return factoryBean;
    }

}