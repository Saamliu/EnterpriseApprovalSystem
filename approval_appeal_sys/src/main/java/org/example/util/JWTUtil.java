package org.example.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.entity.User;

import java.util.Date;
import java.util.Map;

public class JWTUtil {
    private static final String SECRET = "@#￥%*&ADBH"; // 密钥，请替换为实际密钥


    // 创建token令牌
    public static String createToken(User user, long second) {
        // 设置算法和密钥
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 设置过期日期
        Date expiresDate = new Date(System.currentTimeMillis() + second * 1000);
        // 生成JWT
        return JWT.create()
                // 设置载荷:把user里面的字段放入token令牌里面
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("realname", user.getRealname())
                .withClaim("roleId", user.getRoleId())
                // 设置过期时间
                .withExpiresAt(expiresDate)
                // 签发
                .sign(algorithm);
    }

    // 解析token令牌
    public static User parseToken(String token) throws Exception{
        // 设置算法和密钥
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 解析JWT
        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        User user = new User();
        user.setId(claims.get("id").asInt());
        user.setUsername(claims.get("username").asString());
        user.setRealname(claims.get("realname").asString());
        user.setRoleId(claims.get("roleId").asInt());
        return user;
    }

    // 密码加密
    public static String encryptPassword(String origin) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("key", origin)
                .sign(algorithm);
    }

    // 密码解密
    public static String decryptPassword(String encrypted) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(encrypted);
        return jwt.getClaim("key").asString();
    }
}
