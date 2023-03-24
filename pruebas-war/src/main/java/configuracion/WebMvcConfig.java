package configuracion;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import es.pruebas.controller.MarcadorPaqueteWeb;


@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = { MarcadorPaqueteWeb.class })
@EnableAspectJAutoProxy
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ApplicationContext contexto;



	
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {

		registry.addViewController("/accessDenied").setViewName("/error/accessDenied");
		registry.addViewController("/error-not-found").setViewName("/error/404");
		registry.addViewController("/internal-server-error").setViewName("/error/500");
		registry.addViewController("/unauthorized").setViewName("/error/403");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:I18n/configuracion/message", "classpath:I18n/excepciones/message",
				"classpath:I18n/negocio/message", "classpath:I18n/plantilla/message", "classpath:I18n/testing/message",
				"classpath:I18n/validation/message", "classpath:I18n/web/message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/*
	 * @Bean public LocalValidatorFactoryBean validator() {
	 * LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	 * bean.setValidationMessageSource(messageSource()); return bean; }
	 * 
	 * @Override public Validator getValidator() { return validator(); }
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(new Locale("es"));
		return sessionLocaleResolver;
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	

	@Bean
	@Description("Thymeleaf Template Engine")
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		engine.setTemplateEngineMessageSource(messageSource());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType("text/html;charset=UTF-8");

		return resolver;
	}

	@Description("Thymeleaf Template Resolver")
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(contexto);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setTemplateMode("HTML5");
		return resolver;
	}

	
	
	// subida de archivos
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolverConfigure() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(5242880);
	    return multipartResolver;
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}
}