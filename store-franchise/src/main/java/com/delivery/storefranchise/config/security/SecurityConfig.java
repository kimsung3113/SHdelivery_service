package com.delivery.storefranchise.config.security;

import com.delivery.db.storeuser.StoreUserRepository;
import com.delivery.storefranchise.domain.authorization.converter.UserSessionConverter;
import com.delivery.storefranchise.domain.authorization.filter.JwtAuthenticationProcessingFilter;
import com.delivery.storefranchise.domain.authorization.ifs.JwtTokenIfs;
import com.delivery.storefranchise.domain.authorization.service.AuthorizationService;
import com.delivery.storefranchise.domain.authorization.filter.JsonUsernamePasswordAuthenticationFilter;
import com.delivery.storefranchise.domain.authorization.handler.LoginFailureHandler;
import com.delivery.storefranchise.domain.authorization.handler.LoginSuccessJWTProvideHandler;
import com.delivery.storefranchise.domain.storeuser.service.StoreUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthorizationService userDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtTokenIfs jwtTokenService;
    private final StoreUserService storeUserService;
    private final UserSessionConverter userSessionConverter;


    // SWAGGER는 들어 갈 수 있게 제외한다.
    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        //requestCache.setMatchingRequestParameterName(null);

        httpSecurity
                .csrf((csrf) -> csrf.disable())  // CSRF 보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // Http basic Auth 기반으로 로그인 인증창 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 방식 비활성화
                .requestCache(request -> request.requestCache(requestCache)) // URL 뒤의 /?continue test중이니 임의로 없앤다.
                .addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class) // 추가 : 커스터마이징 된 필터를 SpringSecurityFilterChain에 등록
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class) //
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()   // resource에 대해서는 모든 요청 허용
                            // swagger는 인증 없이 통과
                            .requestMatchers(SWAGGER.toArray(new String[0])).permitAll()
                            // open-api / ** 하위 모든 주소는 인증 없이 통과
                            .requestMatchers("/open-api/**", "/login").permitAll()

                            // 그 외 모든 요청은 인증 사용
                            .anyRequest().authenticated()
                    ;
                })
                //.formLogin(Customizer.withDefaults())   // Security 기본 로그인 form 화면
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")      // 로그아웃 성공시 가는 Url
                        .invalidateHttpSession(true))   // 로그아웃 이후 전체 세션 삭제 여부
                .sessionManagement((session) -> session // 세션 생성 및 사용여부에 대한 정책 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않으며, 각 요청은 독립적으로 처리
                ;

        return httpSecurity.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 기본값은 bcrypt
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {//AuthenticationManager 등록
        DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler() {
        return new LoginSuccessJWTProvideHandler(storeUserService, jwtTokenService);
    }

    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }


    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){

        // TODO Converter 말고 다른 곳에서 주입을 받지 않게 할 수 있는지 체크?
        JwtAuthenticationProcessingFilter jsonUsernamePasswordLoginFilter = new JwtAuthenticationProcessingFilter(
                storeUserService, jwtTokenService, userSessionConverter);

        return jsonUsernamePasswordLoginFilter;
    }

}
