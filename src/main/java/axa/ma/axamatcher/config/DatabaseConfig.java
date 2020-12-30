package axa.ma.axamatcher.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration

public class DatabaseConfig {

    @Autowired
    private Environment env;

   
    
    @Value( "${spring.datasource.url}" )
    private String jdbcUrl;
    
    @Value( "${spring.datasource.username}" )
    private String userName;
    
    @Value( "${spring.datasource.password}" )
    private String password;
    
    
    @Bean
   
    public DataSource dataSource() {
	return DataSourceBuilder.create()
	          .driverClassName("org.postgresql.Driver")
	          .url(jdbcUrl)
	          .username(userName)
	          .password(password)
	          .build(); 
    }
    
  

}