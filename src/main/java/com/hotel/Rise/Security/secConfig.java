    package com.hotel.Rise.Security;

    import com.hotel.Rise.Security.User.roomUserDetailsService;
    import com.hotel.Rise.Security.Utils.JWTUtils;
    import com.hotel.Rise.Security.Utils.authTokenFilter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @EnableWebSecurity
    @EnableMethodSecurity
    @Configuration
    public class secConfig {

        @Autowired
        private JWTUtils jwtUtils;
        @Autowired
        private authTokenFilter authTokenFilter;
        @Autowired
        private roomUserDetailsService roomUserDetailsService;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers("/auth/**", "/rooms/**", "/bookings/**").permitAll()
                                    .anyRequest().authenticated())
                    .sessionManagement(manager ->manager
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
        @Bean
        public AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(roomUserDetailsService);
          daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }
