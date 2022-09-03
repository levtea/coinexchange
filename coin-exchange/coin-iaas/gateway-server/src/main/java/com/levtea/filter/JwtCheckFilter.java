package com.levtea.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

  @Autowired private StringRedisTemplate redisTemplate;

  @Value("${no.require.urls:/admin/login}")
  private Set<String> noRequireTokenUris;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (!isRequireToken(exchange)) {
      return chain.filter(exchange);
    }
    String token = getUserToken(exchange);
    if (StringUtils.isEmpty(token)) {
      return buildNoAuthorizationResult(exchange);
    }
    Boolean haskey = redisTemplate.hasKey(token);
    if (haskey != null && haskey) {
      return chain.filter(exchange);
    }

    return buildNoAuthorizationResult(exchange);
  }

  private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    response.getHeaders().set("Content-Type", "application/json");
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("error", "NoAuthorization");
    jsonObject.put("errorMsg", "Token is Null or Error");
    DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
    return response.writeWith(Flux.just(wrap));
  }

  private String getUserToken(ServerWebExchange exchange) {
    String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    return token == null ? null : token.replace("bearer", "");
  }

  private boolean isRequireToken(ServerWebExchange exchange) {
    String path = exchange.getRequest().getURI().getPath();
    if (noRequireTokenUris.contains(path)) {
      return false;
    }
    return Boolean.TRUE;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
