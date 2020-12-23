package sustech.edu.phantom.dboj.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.service.UserService;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .and()
                .addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("msg", "Forbidden");
                    map.put("data", null);
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("msg", "Not authorized");
                    map.put("data", null);
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    String state = new String(httpServletRequest.getInputStream().readAllBytes());
                    User u = (User) authentication.getPrincipal();
                    if (u.getStateSave()) {
                        try {
                            userService.saveState(state, u.getId());
                        } catch (Exception ignored) {
                            ;
                        }
                        log.info("The state of " + u.getUsername() + " has been saved into database.");
                    } else {
                        log.info("The state of " + u.getUsername() + " has not been saved into database.");
                    }
                    log.info(u.getUsername() + " has signed out.");
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("msg", "Log out successfully!");
                    map.put("data", null);
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .deleteCookies("JSESSIONID")
                .permitAll();
        http.sessionManagement().maximumSessions(1);
    }

    private MyAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            Map<String, Object> map = new HashMap<>(2);
            User user = (User) authentication.getPrincipal();
            log.info(user.getState());
            log.info(Arrays.toString(httpServletRequest.getCookies()));
            user.setPassword(null);// 使密码不可见
            map.put("msg", "success");
            map.put("data", user);
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        });
        filter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            Map<String, Object> map = new HashMap<>(2);
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                map.put("msg", "Username or password is wrong");
            } else if (e instanceof DisabledException) {
                map.put("msg", "The account has been invalidated");
            } else {
                map.put("msg", "Logging fails");
            }
            map.put("data", null);
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        });
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/swagger-ui.html",
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-resources/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_TEACHER > ROLE_SA \n ROLE_SA > ROLE_STUDENT";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
