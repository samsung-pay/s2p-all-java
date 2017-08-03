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

package com.samsungpay.s2p.membership.model;

import com.samsungpay.s2p.common.Util;

import java.util.List;

public class CardArt {
    private List<CardArtI18n> i18ns;

    private CardArt() {
    }

    public List<CardArtI18n> getI18ns() {
        return i18ns;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private List<CardArtI18n> i18ns;

        private Builder() {
        }

        public Builder setI18ns(List<CardArtI18n> i18ns) {
            this.i18ns = i18ns;
            return this;
        }

        public CardArt build() throws IllegalArgumentException {
            if (Util.containsNullValue(i18ns))
                throw new IllegalArgumentException("CardArt i18ns is missing");
            CardArt cardArt = new CardArt();
            cardArt.i18ns = i18ns;
            return cardArt;
        }
    }
}
