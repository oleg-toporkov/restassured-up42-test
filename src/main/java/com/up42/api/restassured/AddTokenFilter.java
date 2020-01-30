package com.up42.api.restassured;

import com.up42.api.clients.auth.OAuthClient;
import com.up42.api.constants.Endpoints;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;

import java.time.LocalDateTime;

public class AddTokenFilter implements AuthFilter {

    private ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<LocalDateTime> tokenExpirationDateTime = ThreadLocal.withInitial(() -> null);


    public Response filter(FilterableRequestSpecification filterableRequestSpecification,
                           FilterableResponseSpecification filterableResponseSpecification,
                           FilterContext filterContext) {
        if (!filterableRequestSpecification.getURI().contains(Endpoints.OAUTH_TOKEN) ) {
            if (isTokenExpired()) {
                String token = OAuthClient.loginAsDefaultUser().getAccessToken();

                this.token.set(token);
                this.tokenExpirationDateTime.set(LocalDateTime.now().plusMinutes(5).minusSeconds(30)); // Let's leave 30 second for transport delays
            }

            filterableRequestSpecification.replaceHeader("Authorization", String.format("Bearer %s", this.token.get()));
        }
        return filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
    }

    private boolean isTokenExpired() {
        if (this.tokenExpirationDateTime.get() == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(this.tokenExpirationDateTime.get());
    }
}
