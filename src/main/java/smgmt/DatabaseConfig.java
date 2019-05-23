package smgmt;

import java.util.Properties;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:database.properties")
public class DatabaseConfig {

	@Autowired
	private Environment env;
	
    @Bean
    public DriverManagerDataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("database.driver"));
      dataSource.setUrl(env.getProperty("database.url"));
      dataSource.setUsername(env.getProperty("database.user"));
      dataSource.setPassword(env.getProperty("database.password"));
      return dataSource;
    }
    
	  @Bean public LocalSessionFactoryBean sessionFactory() {
	  LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	  sessionFactory.setDataSource(getDataSource()); Properties hibernateProperties
	  = new Properties();
	  hibernateProperties.put(("hibernate.dialect"),env.getProperty(
	  "hibernate.dialect"));
	  hibernateProperties.put(("hibernate.show_sql"),env.getProperty(
	  "hibernate.show_sql"));
	  hibernateProperties.put(("hibernate.hbm2ddl.auto"),env.getProperty(
	  "hibernate.hbm2ddl.auto"));
	  sessionFactory.setHibernateProperties(hibernateProperties);
	  
	  return sessionFactory; }
	  
	  @Bean public HibernateTransactionManager transactionManager() {
	  HibernateTransactionManager transactionManager = new
	  HibernateTransactionManager();
	  transactionManager.setSessionFactory(sessionFactory().getObject()); return
	  transactionManager; }
	 
}
