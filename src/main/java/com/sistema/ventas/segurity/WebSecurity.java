package com.sistema.ventas.segurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistema.ventas.exceptions.CustomAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
    private JwtFilter jwtFilter;
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        String[] arrServiciosSinSeguridad = {"/autenticacion/login",
        		"/usuarios/recuperarContrasena",
        		"/autenticacion/refreshToken"};	
        
        http.csrf().disable().authorizeRequests().antMatchers(arrServiciosSinSeguridad)
        		.permitAll().
				// Cualquier solicitud a la aplicación debe ser autenticado
				anyRequest().authenticated().and().
				// Un solo punto de entrada para autenticar
				exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and().
				// No se creará ni utilizará ninguna sesión
				sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
    }
}
