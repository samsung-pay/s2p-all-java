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

import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {
    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonProperty("kid")
    private final String kid;

    @JsonProperty("typ")
    private final String typ;

    @JsonProperty("cty")
    private final String cty;

    @JsonProperty("alg")
    private final String alg;

    @JsonProperty("privateClaims")
    private final Map<String, Object> privateClaims;

    public static class Builder {
        /**
         * The key id.
         */
        private String kid;

        /**
         * The content type.
         */
        private String typ;

        /**
         * The content type.
         */
        private String cty;

        /**
         * The signature algorithm.
         */
        private String alg = "RS256";

        /**
         * The private claimed key-values.
         */
        private Map<String, Object> privateClaims;

        private Builder() {
        }

        public Builder keyId(String kid) {
            this.kid = kid;
            return this;
        }

        public Builder type(String typ) {
            this.typ = typ;
            return this;
        }

        public Builder contentType(String cty) {
            this.cty = cty;
            return this;
        }

        // Currently only support RS256Algorithm
        private Builder algorithm(String alg) {
            this.alg = alg;
            return this;
        }

        public Builder privateClaims(Map<String, Object> privateClaims) {
            this.privateClaims = privateClaims;
            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }

    private Header(Builder builder) {
        this.kid = builder.kid;
        this.typ = builder.typ;
        this.cty = builder.cty;
        this.alg = builder.alg;
        this.privateClaims = builder.privateClaims;
    }
}
