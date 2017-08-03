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

package com.samsungpay.s2p.giftcard;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsungpay.s2p.common.AbstractRegistration;
import com.samsungpay.s2p.giftcard.model.Card;
import com.samsungpay.s2p.giftcard.model.Merchant;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Registration extends AbstractRegistration {
    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonProperty("card")
    private Card card;

    @JsonProperty("merchant")
    private Merchant merchant;

    private Registration() {
    }

    public Card getCard() {
        return card;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public static class Builder {
        private Card card;
        private Merchant merchant;

        private Builder() {
        }

        public Builder card(Card card) {
            this.card = card;
            return this;
        }

        public Builder merchant(Merchant merchant) {
            this.merchant = merchant;
            return this;
        }

        public Registration build() throws IllegalArgumentException {
            if (card == null)
                throw new IllegalArgumentException("Registration card is missing");
            if (merchant == null)
                throw new IllegalArgumentException("Registration merchant is missing");

            Registration registration = new Registration();
            registration.card = card;
            registration.merchant = merchant;
            return registration;
        }
    }
}
