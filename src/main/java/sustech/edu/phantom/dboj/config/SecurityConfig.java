package sustech.edu.phantom.dboj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.service.UserService;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
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
//                .formLogin()
//                .loginProcessingUrl("/api/login")
//                .permitAll()
//                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("status", 200);
                    map.put("msg", "Log out successfully!");
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .permitAll();
    }

    private MyAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            Map<String, Object> map = new HashMap<>();
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            map.put("msg", user);
            map.put("status", 200);
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
                "/v2/api-docs", // swagger api json
                "/swagger-resources/configuration/ui", // 用来获取支持的动作
                "/swagger-resources", // 用来获取api-docs的URI
                "/swagger-resources/configuration/security", // 安全选项
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
