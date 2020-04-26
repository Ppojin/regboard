package com.ppojin.regboard.config;

import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Configuration
public class MessageConfiguration implements WebMvcConfigurer {
    //세션에 지역설정
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // default (KOREAN = 'ko')
        slr.setDefaultLocale(Locale.KOREAN);
        return slr;
    }

    // 지역설정 변경 인터셉터
    // 파라미터에 lang 정보 지정 시 언어 변경
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    // 인터셉터를 시스템 레지스트리에 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource(
            @Value("${spring.messages.basename}") String basename,
            @Value("${spring.messages.encoding}") String encoding
    ){
        YamlMessageSource ms = new YamlMessageSource();
        ms.setBasename(basename);
        ms.setDefaultEncoding(encoding);
        ms.setAlwaysUseMessageFormat(true);
        ms.setUseCodeAsDefaultMessage(true);
        ms.setFallbackToSystemLocale(true);
        return ms;
    }

    private static class YamlMessageSource extends ResourceBundleMessageSource{
        @Override
        protected ResourceBundle doGetBundle(String basename, Locale locale)
                throws MissingResourceException {
            return ResourceBundle.getBundle(
                    basename, locale, YamlResourceBundle.Control.INSTANCE);
        }
    }
}
