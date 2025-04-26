package com.bridgelabz.employee_payroll.util;

import com.bridgelabz.employee_payroll.model.EmployeeInfo;
import com.bridgelabz.employee_payroll.model.User;
import com.bridgelabz.employee_payroll.repository.EmployeeRepository;
import com.bridgelabz.employee_payroll.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Date;

@Component
public class JwtUtility {

    @Autowired
    UserRepository userRepository;

    private static final String SECRET_KEY= "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHI";

    private final SecretKey key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateJwt(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1*60*60*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        try{
            System.out.println(token);
            Claims claims= Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
            return claims.getSubject();
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    public boolean validateJwt(String token, String userEmail){
        final String email=extractEmail(token);
        boolean isTokenPresent=true;
        User user=userRepository.findByEmail(email).orElse(null);
        if(user!=null && user.getToken()==null){
            isTokenPresent=false;
        }
        final boolean isExpired=Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
        return (email.equals(userEmail) && !isExpired && isTokenPresent);

    }
}
