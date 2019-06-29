package bank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/user_page").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin_page").hasRole("ADMIN")
                    .antMatchers("/login").permitAll()
                    .antMatchers("/logout").permitAll()
                    .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/process_login")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/user_page")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
