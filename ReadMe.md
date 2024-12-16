Предположим, у нас есть проект freemaker-lib, который содержит кастомную библиотеку для работы с шаблонами Freemarker. 
Мы хотим создать Spring Starter, чтобы упростить интеграцию этой библиотеки в другие приложения.

# Создание стартера

1.1 
Создадим проект `freemaker-starter` в нем необходимо создать файл спринг.факторя
```src/main/resources/META-INF/spring.factories``` который укажет на конфигурацию нашего стартер-приложения. 
Содержимое файла должно быть следующим:
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
mcdodik.freemakerstarter.FreemakerStarterApplication
```
Здесь путь к главному классу можно подкорректировать в зависимости от структуры проекта.

1.2
Укажем зависимости в помнике стартера
```xml
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    
    <dependency>
        <groupId>mcdodik</groupId>
        <artifactId>freemaker-lib</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

1.3
Теперь нам нужно как-то задекларировать бины. Как по мне самый простой способ - через @Configuration класс

```java
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
```
Здесь:
* @ConditionalOnMissingBean гарантирует, что бины будут созданы только если они еще не зарегистрированы в контексте.
* templateLoaderPath указывает на папку, где находятся шаблоны.

В интернете можно прочитать какие кондишаны есть и как ими пользоваться.

Можно еще воспользоваться @Qualifier, что бы разделить, например,
бины для продакшена, для тестового стенда и для локального запуска.

Нужно выполнить mvn install для двух пакетов (lib, starter) для того, 
что бы `mainvebapp` смог взять jar стартера и библиотека из локального репозитория.

Может быть в конкретно этом случаем инсталировать не обязательно, но если мы хотим юзать 
конкретно не понятно где лежащий проект/стартер, то инсталировать его обязательно.

В основном приложении подключаем стартер и радуемся жизни.
```xml
<dependency>
    <groupId>mcdodik</groupId>
    <artifactId>freemaker-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

# А теперь про фримейкер (фримаркер)

Разберем содержимое шаблона [index.ftl](mainwebapp%2Fsrc%2Fmain%2Fresources%2Ftemplates%2Findex.ftl), что бы было понятно какие функции он (заполнятель шаблона) предоставляет

* `"name": "${name}",` - подстановка параметра строкового типа
* `${(age >= 18)?string("true", "false")}` - вычисление логического выражения и конвертация его в строку в зависимости от результата
* `<#if (age >= 18)> "true" <#else> "false" </#if>` - базовый ифчик
* Итератор. Заметьте простановку ",", если есть еще один элемент 
```ftl
<#list hobbies as hobby>
    "${hobby}"<#if hobby_has_next>,</#if>
</#list>
```
* `${features.freeTrial?c}` - конвертация в строку с экранированием символов
* `${.now?string('yyyy-MM-dd HH:mm:ss')}` - получили время и форматнули его в нужный формат
* `${randomId?string}` - якобы не строку форматнули в строку
* Использование вложенных полей. У меня в примере используется Map, но так же прокатит и с полями класса
```ftl
<#list users as user>
    {
        "id": ${user.id},
        "name": "${user.name}",
        "role": "${user.role}"
    }<#if user_has_next>,</#if>
</#list>
```







