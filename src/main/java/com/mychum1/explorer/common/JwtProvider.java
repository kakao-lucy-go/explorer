package com.mychum1.explorer.common;

import com.mychum1.explorer.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private String secretKey;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void jwtProviderInitialize() {
        secretKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());

    }

    public String createToken(String userName) {
        //Claims claims = Jwts.claims().setSubject(userName);
//        claims.put("roles", roles);
//        claims.put("username", userName);
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer("explorer")
                .setSubject(userName)
                .setAudience("explorer")
                .setId(String.valueOf(UUID.randomUUID()))
                // .setClaims(claims)  //payload claim
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS256, secretKey)  //서명
                .compact();
    }

    public String createToken(String userName, List<String> roles) {
        //Claims claims = Jwts.claims().setSubject(userName);
//        claims.put("roles", roles);
//        claims.put("username", userName);
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setIssuer("explorer")
                .setSubject(userName)
                .setAudience("explorer")
                .setId(String.valueOf(UUID.randomUUID()))
               // .setClaims(claims)  //payload claim
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS256, secretKey)  //서명
                .compact();
    }

//    public Authentication getAuthentication(String token) {
//        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//        UserDetails ud = userDetailsService.loadUserByUsername(username);
//        return new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities());
//    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validationToken(String token) {
        System.out.println("token : " + token);

        String replacedToken = token.replace("Bearer ","");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(replacedToken);

        try {
            return !claims.getBody().getExpiration().before(new Date());
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            //parsing 하면서 생기는 exception
            return false;
        }
    }

}
