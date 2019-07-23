package bank.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security Configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsServiceCreator() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceCreator());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .authorizeRequests()
//                        .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                        .antMatchers("/admin/**").hasRole("ADMIN")
//                        .antMatchers("/login").permitAll()
//                        .antMatchers("/home_page").permitAll()
//                        //.antMatchers("/").permitAll()
//                        .antMatchers("/logout").permitAll()
//                    .and()
//                    .formLogin()
//                        .loginPage("/login")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .loginProcessingUrl("/process_login")
//                        .failureUrl("/login?error=true")
//                        .defaultSuccessUrl("/user/user_page")
//                    .and()
//                    .logout()
//                        .logoutUrl("/process_logout")
//                        .logoutSuccessUrl("/login")
//                        .deleteCookies("JSESSIONID")
//                    ;
//
//    }
    

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
	  http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/admin/**").hasRole("ADMIN")
      .antMatchers("/login*").permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/process_login")
				.defaultSuccessUrl("/success", true)
      .failureUrl("/login?error=true")
      .and()
      .logout()
      .logoutUrl("/perform_logout")
      .deleteCookies("JSESSIONID");
  }

    
}


