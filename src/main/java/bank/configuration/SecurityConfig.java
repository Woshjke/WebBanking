package bank.configuration;

import bank.RequestParameter;
import bank.services.dbServices.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserDaoService userDaoService;

    @Autowired
    public SecurityConfig(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @Bean
    public UserDetailsService userDetailsServiceCreator() {
        return new UserDetailsServiceImpl(userDaoService);
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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login_page").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/favicon.ico").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/doRegister").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/user_details").permitAll()
                    .antMatchers("/userDetails").permitAll()
                    .antMatchers("/activate_account/*").permitAll()
                    .antMatchers("/after_activation_page").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/process_login")
                    .failureUrl("/login?error=" + RequestParameter.TRUE.getValue())
                    .defaultSuccessUrl("/user/user_page")
                .and()
                .logout()
                    .logoutUrl("/process_logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/403");
    }
}


