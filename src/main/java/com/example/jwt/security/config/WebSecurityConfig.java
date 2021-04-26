package com.example.jwt.security.config;

import com.example.jwt.entity.account.LogOutUser;
import com.example.jwt.repository.LogOutUserRepository.LogOutUserRepository;
import com.example.jwt.security.filter.AjaxLoginProcessingFilter;
import com.example.jwt.security.handler.CustomLoginFailureHandler;
import com.example.jwt.security.handler.CustomLoginSuccessHandler;
import com.example.jwt.security.provider.AjaxAuthenticationProvider;
import com.example.jwt.security.service.CustomTokenExtractor;
import com.example.jwt.security.service.UserDetailsServiceImpl;
import com.example.jwt.security.util.jwt.accesToken.TokenConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.jwt.security.util.jwt.GetTokenInfo.getUserName;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final LogOutUserRepository logOutUserRepository;

    private CustomTokenExtractor tokenExtractor = new CustomTokenExtractor();

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
        .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
        .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .formLogin().disable()
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .logout()
                .logoutUrl("/api/logout")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        String username = getUserName(tokenExtractor.getTokenFromRequest(request, TokenConstant.AUTH_HEADER));
                        LogOutUser user = logOutUserRepository.findByUsername(username);
                        if(user != null && !user.getIsLogOut()) {
                            logOutUserRepository.delete(user);
                        }
                        Cookie cookie = new Cookie("Authorization", null);
                        response.addCookie(cookie);
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                });
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxAuthenticationFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxAuthenticationFilter = new AjaxLoginProcessingFilter();
        ajaxAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        ajaxAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        ajaxAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        ajaxAuthenticationFilter.afterPropertiesSet();
        return ajaxAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider());
        auth.userDetailsService(userDetailsService);
    }


}
