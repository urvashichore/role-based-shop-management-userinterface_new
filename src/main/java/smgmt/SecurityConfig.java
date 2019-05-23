package smgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
    
    @Autowired
    private DriverManagerDataSource dataSource;
	
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
        .antMatchers("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/user").hasAnyRole("USER")
        .antMatchers("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/admin").hasAnyRole("ADMIN")
        .antMatchers("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/billinguser").hasAnyRole("BILLINGUSER")
        .antMatchers("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/accessdenied").permitAll()
        .anyRequest().authenticated().and().formLogin().loginPage("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/login").loginProcessingUrl("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/j_spring_security_check").successHandler(myAuthenticationSuccessHandler()).failureForwardUrl("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT/accessdenied")
        .permitAll().and().logout().permitAll();
        http.csrf().disable();
        /*http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();*/
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication().withUser("user").password("{noop}user")
		 * .authorities("ROLE_USER").and().withUser("admin").password("{noop}admin")
		 * .authorities("ROLE_ADMIN").and().withUser("billinguser").password(
		 * "{noop}billinguser") .authorities("ROLE_BILLINGUSER");
		 */
        //auth.inMemoryAuthentication().withUser("javacodegeeks").password("very_secure").roles("USER");
		  auth.jdbcAuthentication().dataSource(dataSource)
		  .usersByUsernameQuery("select username, password, active from users where username=?"
		  )
		  .authoritiesByUsernameQuery("select us.username, ur.authority from users us, user_roles ur where us.userid = ur.userid and us.username =?"
		  );
		 
    }
}
