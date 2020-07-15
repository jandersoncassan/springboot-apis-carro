package com.br.carro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.carro.security.jwt.JwtAuthenticationFilter;
import com.br.carro.security.jwt.JwtAuthorizationFilter;
import com.br.carro.security.jwt.handler.AccessDeniedHandler;
import com.br.carro.security.jwt.handler.UnauthorizedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Removi segurança para subir o servidor.
       /* http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable();*/

		 /* BASIC      
		   http.authorizeRequests()
		                .anyRequest()
		                .authenticated()
		                .and().httpBasic() //isso define o tipo de autenticação
		                .and().csrf().disable(); //isso permite a execução d
		*/        
        
        AuthenticationManager authManager = authenticationManager();
        http.authorizeRequests() //AUTORIZANDO AS REQUISIÇÕES
            .antMatchers(HttpMethod.GET, "/api/v1/token").permitAll() //PARA O token ESTAMOS AUTORIZANDO
            .antMatchers("/","/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
            .anyRequest().authenticated() //QUALQUER REQUISIÇÃO PRECISA ESTAR AUTENTICADA, PRECISA FICAR AQUI EM BAIXO DEPOIS DAS PERMITALL
            .and().csrf().disable()
            .addFilter(new JwtAuthenticationFilter(authManager)) //ADICIONANDO O FILTER DE AUTENTICAÇÃO
            .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService)) //ADICIONANDO O FILTER DE AUTORIZAÇÃO
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler) //handler para acesso negado
            .authenticationEntryPoint(unauthorizedHandler) //handler para não autorizado
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //DESLIGANDO A CRIAÇÃO DE COOKIES
}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
 /*       auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("user").password(encoder.encode("user")).roles("USER")
                .and()
                .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
*/    }

}
