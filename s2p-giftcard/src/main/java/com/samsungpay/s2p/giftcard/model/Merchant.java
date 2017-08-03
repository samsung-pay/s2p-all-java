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

package com.samsungpay.s2p.giftcard.model;

public class Merchant {
    private String name;
    private String logoUrl;

    private Merchant() {}

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String logoUrl;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Merchant build() throws IllegalArgumentException {
            if (name == null || name.isEmpty())
                throw new IllegalArgumentException("Merchant name is missing");
            if (logoUrl == null || logoUrl.isEmpty())
                throw new IllegalArgumentException("Merchant logo url is missing");

            Merchant merchant = new Merchant();
            merchant.name = name;
            merchant.logoUrl = logoUrl;
            return merchant;
        }
    }
}
