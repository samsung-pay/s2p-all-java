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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsungpay.s2p.common.model.I18n;

public class CardArtI18n extends I18n<CardArtI18n> {
    private String artUrl;

    private CardArtI18n(@JsonProperty("language") String language) {
        super(language);
    }

    public String getArtUrl() {
        return artUrl;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String language;
        private String artUrl;

        private Builder() {
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setArtUrl(String artUrl) {
            this.artUrl = artUrl;
            return this;
        }

        public CardArtI18n build() throws IllegalArgumentException {
            if (artUrl == null || artUrl.isEmpty())
                throw new IllegalArgumentException("CardArtI18n artUrl is missing");
            CardArtI18n i18n = new CardArtI18n(language);
            i18n.artUrl = artUrl;
            return i18n;
        }
    }
}
