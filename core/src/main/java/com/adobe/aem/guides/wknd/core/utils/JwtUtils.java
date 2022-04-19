package com.adobe.aem.guides.wknd.core.utils;


//import io.jsonwebtoken.Jwts;
//import com.adobe.granite.oauth.jwt.JwsBuilder;
//import com.adobe.granite.oauth.jwt.JwsBuilderFactory;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.oltu.oauth2.jwt.JWT;
//
//
//import javax.servlet.http.Cookie;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//public class JwtUtils {
//
//    public static String createToken(String username) {
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuer("localhost:4502")
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
//                        .atZone(ZoneId.systemDefault())
//                        .toInstant()))
//                .signWith(Key.KEY.getPrivate(), SignatureAlgorithm.RS512)
//                .compact();
//    }
//
//    public static boolean validateToken(String jwtToken) {
//        try{
//            Jwts.parserBuilder()
//                    .setSigningKey(Key.KEY.getPrivate())
//                    .build()
//                    .parseClaimsJws(jwtToken);
//            return true;
//        }catch(Exception e){
//            return false;
//        }
//    }
//}
