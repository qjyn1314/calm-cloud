package com.calm.parent.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 * explain: jwt工具类
 * </p>
 *
 * @author wangjunming
 * @since 2020/3/23 12:26
 */
@Slf4j
@Component
public final class JwtUtils {

    /**
     * jwt密钥
     */
    public static final String SECRET = "Calm_202103021739";

    /**
     * jwt过期时间-默认是24小时
     */
    public static final int EXPIRE_TIME = 24;

    /**
     * jwt签发者
     */
    public static final String ISSUSER = "Mr.Wang";

    private static final Clock CLOCK = DefaultClock.INSTANCE;

    /**
     * 初始化jwt密钥和过期时间
     * 主要用于在用户登录过后的信息存储，需要将生成的token返回给前端，并在每次请求的时候，在请求头添加此数据信息
     *
     * @author wangjunming
     * @since 2020/3/23 13:11
     */
    public JwtUtils() {
    }

    /**
     * Map转实体类
     *
     * @param map         需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entityClass 需要转化成的实体类
     * @author wangjunming
     * @since 2020/3/23 14:18
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entityClass) {
        T t = null;
        try {
            t = entityClass.newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            log.error("解析map  到  entity  失败-InstantiationException", e);
        } catch (IllegalAccessException e) {
            log.error("解析map  到  entity  失败-IllegalAccessException", e);
        } catch (Exception e) {
            log.error("解析map  到  entity  失败-Exception", e);
        }
        return t;
    }

    /**
     * 封装生成token
     *
     * @param claims     存储的信息
     * @param subject    主题
     * @param expireSecs 失效时间，单位：小时
     * @author wangjunming
     * @since 2020/3/23 12:53
     */
    public static String generateToken(Map<String, Object> claims, String subject, int expireSecs) {
        final Calendar nowCalendar = Calendar.getInstance();
        final Date createdDate = nowCalendar.getTime();
        nowCalendar.add(Calendar.HOUR, expireSecs);
        final Date expirationDate = nowCalendar.getTime();
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(ISSUSER)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * 获取jwt的payload部分
     *
     * @author wangjunming
     * @since 2020/3/23 13:16
     */
    public static Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成任意token
     *
     * @param claims 需要存储的有效信息
     * @author wangjunming
     * @since 2020/3/23 12:54
     */
    public static String doGenerateToken(Map<String, Object> claims) {
        return generateToken(claims, SECRET, EXPIRE_TIME);
    }

    /**
     * 获取生成的任意token中存储的所有信息
     *
     * @param token 存储有效信息的token
     * @author wangjunming
     * @since 2020/3/23 12:54
     */
    public Claims getGenerateToken(String token) {
        return getClaimFromToken(token);
    }

    /**
     * 解析token是否正确
     *
     * @author wangjunming
     * @since 2020/3/23 13:16
     */
    public static boolean parseToken(String token) {
        boolean flag = false;
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            flag = true;
        } catch (ExpiredJwtException e) {
            log.error("JWT-解析token异常-ExpiredJwtException：", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT-解析token异常-UnsupportedJwtException：", e);
        } catch (MalformedJwtException e) {
            log.error("JWT-解析token异常-MalformedJwtException：", e);
        } catch (SignatureException e) {
            log.error("JWT-解析token异常-SignatureException：", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT-解析token异常-IllegalArgumentException：", e);
        }
        return flag;
    }

    /**
     * token是否过期
     *
     * @param token jwt生成的Token值
     * @return Boolean true-已过期；false-未过期
     */
    public static Boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(CLOCK.now());
    }

    /**
     * 获取Claims的信息
     *
     * @param token          jwt生成的Token值
     * @param claimsResolver Token 解析出来的payload信息
     * @param <T>            payload信息中所包含的字段
     * @return T
     */
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimFromToken(token);
        return claimsResolver.apply(claims);
    }


}
