package inha.tnt.hbc.security;

import static inha.tnt.hbc.domain.member.entity.MemberRoles.*;
import static inha.tnt.hbc.util.Constants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.security.jwt.JwtAuthenticationFilter;
import inha.tnt.hbc.security.jwt.JwtAuthenticationProvider;
import inha.tnt.hbc.security.matcher.SkipPathRequestMatcher;
import inha.tnt.hbc.security.oauth2.CustomOAuth2UserService;
import inha.tnt.hbc.security.oauth2.OAuth2SuccessHandler;
import inha.tnt.hbc.util.JwtUtils;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST_SWAGGER = {"/v2/api-docs", "/configuration/ui",
		"/swagger-resources/**", "/configuration/security", "/swagger-ui.html/**", "/webjars/**", "/swagger/**"};
	private static final String[] AUTH_WHITELIST_GUEST = {"/", "/csrf", "/error", "/auth/**", "/oauth2/**",
		"/token/reissue", "/batch/**", "/admins/**", "/test/**"};
	private static final String OAUTH2_REDIRECT_URI = "/oauth2/signin/*";
	private static final String OAUTH2_AUTHORIZE_URI = "/oauth2/authorize";

	private final JwtUtils jwtUtils;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final CustomOAuth2UserService oAuth2UserService;

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(AUTH_WHITELIST_SWAGGER);
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler() {
		return new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
	}

	@Bean
	public CorsConfigurationSource configurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern(CorsConfiguration.ALL);
		configuration.addAllowedHeader(CorsConfiguration.ALL);
		configuration.addAllowedMethod(CorsConfiguration.ALL);
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(ALL_PATTERN, configuration);

		return source;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		final List<String> skipPaths = new ArrayList<>();
		skipPaths.addAll(Arrays.stream(AUTH_WHITELIST_SWAGGER).collect(Collectors.toList()));
		skipPaths.addAll(Arrays.stream(AUTH_WHITELIST_GUEST).collect(Collectors.toList()));
		final RequestMatcher matcher = new SkipPathRequestMatcher(skipPaths);
		final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtUtils);
		filter.setAuthenticationManager(super.authenticationManager());
		filter.setAuthenticationFailureHandler(authenticationEntryPointFailureHandler());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.formLogin()
			.disable()
			.httpBasic()
			.disable()
			.logout()
			.disable()
			.cors()
			.configurationSource(configurationSource())
			.and()

			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler)
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.authorizeRequests()
			.requestMatchers(CorsUtils::isPreFlightRequest)
			.permitAll()
			.antMatchers("/members/**", "/token/check", "/rooms/**", "/messages/**", "/files/**", "/alarms/**")
			.hasAuthority(ROLE_USER.name())
			.antMatchers(AUTH_WHITELIST_GUEST)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()

			/*.oauth2Login()
			.authorizationEndpoint().baseUri(OAUTH2_AUTHORIZE_URI)
			.and()
			.redirectionEndpoint().baseUri(OAUTH2_REDIRECT_URI)
			.and()

			.successHandler(oAuth2SuccessHandler)
			.failureHandler(authenticationEntryPointFailureHandler())
			.userInfoEndpoint().userService(oAuth2UserService)*/
		;

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
