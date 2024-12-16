package mcdodik.freemakerstarter.beans;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FreeMarkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(freemarker.template.Configuration.class)
    public Configuration freemarkerConfig() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_33);

        try {
            configuration.setDirectoryForTemplateLoading(new java.io.File("classpath:/templates/"));
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setWhitespaceStripping(true);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

}