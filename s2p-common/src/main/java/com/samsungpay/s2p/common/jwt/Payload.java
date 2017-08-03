/*
 * Copyright 2017 Samsung Pay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.samsungpay.s2p.common.jwt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {
    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonProperty("iss")
    private final String iss;

    @JsonProperty("sub")
    private final String sub;

    @JsonProperty("aud")
    private final String aud;

    @JsonProperty("exp")
    private final Date exp;

    @JsonProperty("nbf")
    private final Date nbf;

    @JsonProperty("iat")
    private final Date iat;

    @JsonProperty("jti")
    private final String jti;

    @JsonProperty("claims")
    private final Map<String, Object> claims;

    public static class Builder {

        /**
         * The issuer.
         */
        private String iss;

        /**
         * The subject.
         */
        private String sub;

        /**
         * The audience.
         */
        private String aud;

        /**
         * The expiration date.
         */
        private Date exp;

        /**
         * The not before date.
         */
        private Date nbf;

        /**
         * The issued at date.
         */
        private Date iat;

        /**
         * The JWT id.
         */
        private String jti;

        /**
         * The Issuer.
         */
        private Map<String, Object> claims;

        private Builder() {
        }

        public Builder issuer(String iss) {
            this.iss = iss;
            return this;
        }

        public Builder subject(String sub) {
            this.sub = sub;
            return this;
        }

        public Builder audience(String aud) {
            this.aud = aud;
            return this;
        }

        public Builder expirationTime(Date exp) {
            this.exp = exp;
            return this;
        }

        public Builder notBefore(Date nbf) {
            this.nbf = nbf;
            return this;
        }

        public Builder issuedAt(Date iat) {
            this.iat = iat;
            return this;
        }

        public Builder jwtId(String jti) {
            this.jti = jti;
            return this;
        }

        public Builder withSingleClaim(String key, Object val) {
            Map<String, Object> singleClaim = new ConcurrentHashMap<String, Object>(1);
            singleClaim.put(key, val);
            this.claims = singleClaim;
            return this;
        }

        public Builder claims(Map<String, Object> claims) {
            this.claims = claims;
            return this;
        }

        public Payload build() {
            return new Payload(this);
        }
    }

    private Payload(Builder builder) {
        this.iss = builder.iss;
        this.sub = builder.sub;
        this.aud = builder.aud;
        this.exp = builder.exp;
        this.nbf = builder.nbf;
        this.iat = builder.iat;
        this.jti = builder.jti;
        this.claims = builder.claims;
    }
}
