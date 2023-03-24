package configuracion;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import es.pruebas.entidades.MarcadorPaqueteEntidades;


  
  @Configuration
  
  @PropertySource("file:/${apps_config_path}/pruebas/pruebas.properties")
  
  @EnableTransactionManagement
  
  @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
  transactionManagerRef = "transactionManager", basePackageClasses = {
  MarcadorRepositoriosJpa.class })
  
  @EnableJpaAuditing(auditorAwareRef = "auditorProvider") public class
  PersistenciaConfig {
  
  @Autowired private Environment env;
  
  
  @Bean("dataSourceOracleXilas") public DataSource dataSource() { final
  JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
  dsLookup.setResourceRef(true); return
  dsLookup.getDataSource(env.getProperty("jdbc.jndi.name")); }
  
  @Bean("entityManagerFactory") public LocalContainerEntityManagerFactoryBean
  entityManagerFactoryDevLocal(
  
  @Qualifier("dataSourceMySqlPruebas") DataSource dataSource) {
  LocalContainerEntityManagerFactoryBean emf = new
  LocalContainerEntityManagerFactoryBean(); emf.setDataSource(dataSource);
  emf.setPackagesToScan(MarcadorPaqueteEntidades.class.getPackage().getName());
  emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
  emf.setJpaProperties(hibernateProperties()); return emf; }
  
  @Bean(name = "transactionManager") public JpaTransactionManager
  transactionManager(
  
  @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory)
  { JpaTransactionManager transactionManager = new JpaTransactionManager();
  transactionManager.setEntityManagerFactory(entityManagerFactory);
  
  return transactionManager; }
  
  @Bean public PersistenceExceptionTranslationPostProcessor
  exceptionTranslation() { return new
  PersistenceExceptionTranslationPostProcessor(); }
  
  private Properties hibernateProperties() {
  
  Properties properties = new Properties();
  properties.setProperty("hibernate.dialect",
  env.getProperty("org.hibernate.dialect.MySQL5Dialect"));
  properties.setProperty("hibernate.show_sql",
  env.getProperty("hibernate.show_sql"));
  properties.setProperty("hibernate.format_sql",
  env.getProperty("hibernate.format_sql"));
  properties.setProperty("hibernate.hbm2ddl.auto",
  env.getProperty("hibernate.hbm2ddl.auto"));
  
  return properties; } }
 