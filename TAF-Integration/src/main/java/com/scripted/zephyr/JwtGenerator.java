package com.scripted.zephyr;

import java.net.URI;


public interface JwtGenerator {

    String generateJWT(String requestMethod, URI uri, int jwtExpiryWindowSeconds);
}
