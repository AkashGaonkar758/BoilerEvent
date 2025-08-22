package com.example.campusevents.User.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.example.campusevents.Security.CustomUserDetailsService;
import com.example.campusevents.Security.Jwt.JwtUtil;
import com.example.campusevents.User.DTO.AuthRequestDTO;
import com.example.campusevents.User.DTO.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // Set to true in production with HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days in seconds
            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthResponseDTO(accessToken));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || !jwtUtil.isTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username);

        return ResponseEntity.ok(new AuthResponseDTO(newAccessToken));
    }


}
