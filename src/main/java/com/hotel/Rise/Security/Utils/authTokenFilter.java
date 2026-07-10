    package com.hotel.Rise.Security.Utils;

    import com.hotel.Rise.Security.User.roomUserDetailsService;
    import io.jsonwebtoken.JwtException;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Component;
    import org.springframework.util.StringUtils;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;


    @Component
    public class authTokenFilter extends OncePerRequestFilter {

        @Autowired
        private JWTUtils jwtUtils;
        @Autowired
        private roomUserDetailsService roomUserDetailsService;

        @Override
        protected void doFilterInternal
                (
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {

            String jwt = parseToken(request);
            try {
                if(StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)){
                String userName =  jwtUtils.getUserNameFromToken(jwt);
                UserDetails userDetails = roomUserDetailsService.loadUserByUsername(userName);
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
                return;
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(e.getMessage());
                return;
            }
            filterChain.doFilter(request, response);
        }
        public String parseToken(HttpServletRequest request) {
            String token = request.getHeader("Authorization");
            if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                return token.substring(7);
            }
            return null;
        }
    }
