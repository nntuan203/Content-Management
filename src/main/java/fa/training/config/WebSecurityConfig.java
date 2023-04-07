package fa.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login-fail","/logout-success" , "/register", "/process_register").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login-fail")

                .and()
                .logout()
                .deleteCookies("my-remember-me", "JSESSIONID")
                .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)

                .and()
                .rememberMe()
                .key("mySecretKey")
                .tokenValiditySeconds(60 * 10)
                .rememberMeCookieName("my-remember-me")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    class PlainPasswordEncoder extends BCryptPasswordEncoder {
//        @Override
//        public boolean matches(CharSequence rawPassword, String encodedPassword) {
//            return rawPassword.toString().equals(encodedPassword);
//        }
//
//        @Override
//        public String encode(CharSequence rawPassword) {
//            return rawPassword.toString();
//        }
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new PlainPasswordEncoder();
//    }
}
