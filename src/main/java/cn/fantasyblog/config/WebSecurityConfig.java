package cn.myBlog.config;

import cn.myBlog.security.MyAccessDeniedHandler;
import cn.myBlog.security.MyAuthenticationFailureHandler;
import cn.myBlog.security.MyAuthenticationSuccessHandler;
import cn.myBlog.security.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


/**
 * @Description 安全配置
 * @Author Cy
 * @Date 2021-03-20 15:41
 */
// WebSecurityConfigurerAdapter是个适配器
// 启动基于注解的安全性
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    private final MyAccessDeniedHandler myAccessDeniedHandler;

    private final UserDetailsService userDetailsService;

    private final ValidateCodeFilter validateCodeFilter;

    public WebSecurityConfig(MyAuthenticationSuccessHandler myAuthenticationSuccessHandler,
                             MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                             MyAccessDeniedHandler myAccessDeniedHandler,
                             @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                             ValidateCodeFilter validateCodeFilter) {
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
        this.validateCodeFilter = validateCodeFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 认证用户来源
    // AuthenticationManager: 认证的核心接口.
    // AuthenticationManagerBuilder: 用于构建AuthenticationManager对象的工具.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义认证规则
        // AuthenticationProvider: ProviderManager持有一组AuthenticationProvider,每个AuthenticationProvider负责一种认证.
        auth.authenticationProvider(authenticationProvider());
    }

    // 解决UserDetailsService中抛出的UsernameNotFoundException无法被捕获
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    /**
     * 配置Spring Security，下面说明几点注意事项。
     * 1. Spring Security 默认是开启了CSRF的，此时咱们提交的POST表单必须有隐藏的字段来传递CSRF，
     * 并且在logout中，咱们必须经过POST到/logout的方法来退出用户
     * 2. 开启了rememberMe()功能后，咱们必须提供rememberMeServices
     * 并且咱们只能在TokenBasedRememberMeServices中设置cookie名称、过时时间等相关配置,若是在别的地方同时配置，会报错。
     * 错误示例：xxxx.and().rememberMe().rememberMeServices(getRememberMeServices()).rememberMeCookieName("cookie-name")
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //放行静态资源和登录请求
                .antMatchers( // 允许对于网站静态资源的无授权访问
                        "/static/**",
                        "/admin/login", "/admin/login.html", "/admin/captcha").permitAll()
                //其余请求在认证后访问
                .antMatchers("/admin/**").authenticated();

        // 自定义拦截器，添加图形验证码
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 允许表单登录
                .formLogin()
                //自定义登录页面
                .loginPage("/admin/login.html")
                //自定义登录表单提交路径
                .loginProcessingUrl("/admin/login")
                //认证成功处理
                .successHandler(myAuthenticationSuccessHandler)
                //认证失败处理
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .rememberMeServices(getRememberMeServices())// 必须提供
                .key(SECRET_KEY)// 此SECRET须要和生成TokenBasedRememberMeServices的密钥相同
                .and()
                // Security底层默认会拦截/logout请求因此需要覆盖它的默认逻辑
                .logout().permitAll()
                .logoutUrl("/admin/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/admin/login.html");

        // 处理权限异常
//        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        //禁用拦截除GET方式以外的请求即关闭打开的csrf保护
        http.csrf().disable();

        //解决X-Frame-Options DENY问题
        http.headers().frameOptions().sameOrigin();
    }

    private final String SECRET_KEY = "123456";

    /**
     * 若是要设置cookie过时时间或其余相关配置，请在下方自行配置
     */
    private TokenBasedRememberMeServices getRememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(SECRET_KEY, userDetailsService);
        services.setCookieName("remember-cookie");
//        services.setTokenValiditySeconds(100); // 默认14天
        return services;
    }
}
