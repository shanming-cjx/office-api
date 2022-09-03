package com.chenjx.office.api.config;

import com.chenjx.office.api.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security配置类
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 使用BCryptPasswordEncoder进行密码的加密和校验。
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 使用对登录接口放行。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()//仅匿名可访问，携带token不能访问
                .antMatchers("/swagger-ui/**",
//                        "/swagger-ui/index.html",
                        "/swagger-ui.html",
//                        "/v3/api-docs/**",
//                        "/v3/api-docs",
                        "/doc-api.html/**").permitAll()//无论是否携带token都可访问
                // 除上面外的所有请求全部需要鉴权认证才能访问
                .anyRequest().authenticated();
//                .and()
//                .httpBasic();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //认证失败的异常处理配置
//        http.exceptionHandling()
//                        .authenticationEntryPoint(authenticationEntryPoint)//认证失败处理
//                        .accessDeniedHandler(accessDeniedHandler);//权限不足处理
        //允许跨域
        http.cors();
    }

/*    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置跳过security验证拦截的路径，配置的放行路径
        web.ignoring().antMatchers(
                "/swagger-ui/index.html",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/v3/api-docs",
                "/doc-api.html/**",
                "/doc-api.html/swagger-config"
        );
    }*/

    /**
     * 使用AuthenticationManager进行登录校验操作。
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}