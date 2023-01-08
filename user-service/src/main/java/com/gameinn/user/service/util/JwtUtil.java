package com.gameinn.user.service.util;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String secret = "mySecret";

    public static String getToken(HttpServletRequest request){
        String authorizationHeader = getHeadersInfo(request).get("authorization");
        return authorizationHeader.split(" ")[1];
    }

    public static String getSubject(String jwt){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }

    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
