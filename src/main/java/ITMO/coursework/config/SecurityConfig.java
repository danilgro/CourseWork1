package ITMO.coursework.config;

import ITMO.coursework.model.db.entity.people.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ITMO.coursework.service.people.WorkerDetailsService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;
import java.security.Security;

import static org.springframework.http.HttpMethod.POST;

@Configuration      // класс является конфигурационным bean
@EnableWebSecurity  // показывает, что данному классу будет применён WebSecurity
// @EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService() {
//        // UserDetailsService   это компонент в Spring Security, который используется для загрузки пользовательских данных.
//      //  UserDetails admin = Worker.builder().name("admin").password(encoder.encode("admin")).build();
//        //return  new InMemoryUserDetailsManager(admin);
//        return new WorkerDetailsService();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.requestMatchers("app/general","app/v1/result").permitAll()
//                        // по контрольной точке application/home нас пускают без регистрации
//                        .requestMatchers("app/v1/**").authenticated())
//                //  ** означают все пути далее,  доступ для зарегестрированных user
//             .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                // доступ к форме регистрации всем желающим
//                .build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//

}
