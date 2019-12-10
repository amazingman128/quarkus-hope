package com.smartbird.util;

import cn.hutool.core.map.MapUtil;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 */
public class JwtUtil {


    private JwtUtil() {
        // no-op: utility class
    }


    public static String generateTokenString(int minutes, Map<String, Object> payloadClaims) {
        // 默认过期时间为60分钟
        if (minutes <= 0) {
            minutes = 60;
        }
        if (MapUtil.isEmpty(payloadClaims)) {
            payloadClaims = new HashMap<>();
        }

        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        PrivateKey pk = null;
        try {
            pk = readPrivateKey("/META-INF/resources/privateKey.pem");
        } catch (Exception e) {
            throw new IllegalStateException("无法获取JWT证书文件");
        }


        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Issuer");  // who creates the token and signs it
        claims.setAudience("Audience"); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(minutes); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject("subject"); // the subject/principal is whom the token is about
        for (String key : payloadClaims.keySet()) {
            claims.setClaim(key, payloadClaims.get(key)); // additional claims/attributes about the subject can be added
        }
        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(pk);

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(SnowFlakeUtil.getDistributedID());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        String jwt = "";
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new IllegalStateException("JWT生成失败");
        }
        return jwt;
    }

    /**
     * 验证jwt token
     *
     * @param token jwt token
     * @return Jws对象
     */
    public static JwtClaims verify(String token) throws Exception {
        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
                .setExpectedAudience("Audience") // to whom the JWT is intended for
                .setVerificationKey(readPublicKey("/META-INF/resources/publicKey.pem")) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build(); // create the JwtConsumer instance

        //  Validate the JWT and process it to the Claims
        return jwtConsumer.processToClaims(token);
    }


    /**
     * 阅读在类路径的私钥
     *
     * @param pemResName - 密钥文件的资源名称
     * @return PrivateKey
     * @throws Exception 在解码失败
     */
    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        InputStream contentIS = JwtUtil.class.getResourceAsStream(pemResName);
        byte[] tmp = new byte[4096];
        int length = contentIS.read(tmp);
        return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }

    /**
     * 阅读在类路径的公钥
     *
     * @param pemResName - 密钥文件的资源名称
     * @return PrivateKey
     * @throws Exception 在解码失败
     */
    public static PublicKey readPublicKey(final String pemResName) throws Exception {
        InputStream contentIS = JwtUtil.class.getResourceAsStream(pemResName);
        byte[] tmp = new byte[4096];
        int length = contentIS.read(tmp);
        return decodePublicKey(new String(tmp, 0, length, "UTF-8"));
    }

    /**
     * 解码密钥字符串的RSA公钥
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PublicKey decodePublicKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(pubKeySpec);

    }


    /**
     * 解码密钥字符串的RSA私钥
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    /**
     * 使用base64解密证书内容
     *
     * @param pemEncoded
     * @return
     */
    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }


    /**
     * 移除证书的头部与尾部信息
     *
     * @param pem
     * @return
     */
    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    /**
     * 以秒为单位获取当前时间戳
     *
     * @return the current time in seconds since epoch
     */
    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}
