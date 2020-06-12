package com.diselec.config;

import com.diselec.service.UserSecurityService;
import com.diselec.utility.EncriptarUtility;
import com.diselec.utility.MensajeExitosoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class PermisosConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityServiceObj;

    @Autowired
    private MensajeExitosoLogin mensajeExito;

    private BCryptPasswordEncoder passwordEncoder(){
        return EncriptarUtility.passwordEncoder();
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/img/**",
            "/vendor/**",
            "/fonts/**",
            "/",
            "/index",
            "/login",
            "/servicios",
            "/contacto",
            "/registro",
            "/registroNew",
            "/olvideContraseña",
            "/recuperarContraseña",
            "/catalogoProductos",
            "/listaProductos"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS)
                .permitAll()
                .anyRequest()
                .authenticated();

        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .formLogin()
                .failureUrl("/login?error")
                .successHandler(mensajeExito)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")
                .deleteCookies("remember-me")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userSecurityServiceObj).passwordEncoder(passwordEncoder());
    }
}
