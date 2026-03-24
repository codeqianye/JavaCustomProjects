package com.feng.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
@Slf4j
public class CustomGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("==========CustomGateWayFilter=========== ");
        String uname = exchange.getRequest().getQueryParams().getFirst("user");
        String pwd = exchange.getRequest().getQueryParams().getFirst("pwd");
        if (!("hspedu".equals(uname) && "123456".equals(pwd))) {
            System.out.println("=========非法用户=============");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//回应
            return exchange.getResponse().setComplete();
        }
        //验证通过，放行
        return chain.filter(exchange);
    }

    /**
     * order 表示该过滤器执行的顺序，数字越小，优先级越高. * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}