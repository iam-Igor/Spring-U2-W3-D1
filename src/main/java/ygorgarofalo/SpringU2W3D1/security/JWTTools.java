package ygorgarofalo.SpringU2W3D1.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.UnhautorizedExc;

import java.util.Date;

@Component
public class JWTTools {


    @Value("${JWT_SECRET}")
    private String secret;


    public String createToken(User user) {

        return Jwts.builder().subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) { // Dato un token mi lancia eccezioni in caso di token manipolato/scaduto
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnhautorizedExc("Problemi col token! Per favore effettua di nuovo il login!");
        }
    }


    //Estraggo id user dal toker
    public String extractIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

}
