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

public class CustomClaimI18n extends I18n<CustomClaimI18n> {
    private String label;
    private String content;

    private CustomClaimI18n(@JsonProperty("language") String language) {
        super(language);
    }

    public String getLabel() {
        return label;
    }

    public String getContent() {
        return content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String language;
        private String label;
        private String content;

        private Builder() {
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public CustomClaimI18n build() throws IllegalArgumentException {
            if (label == null)
                throw new IllegalArgumentException("CustomClaim label is missing");
            if (content == null)
                throw new IllegalArgumentException("CustomClaim content is missing");

            CustomClaimI18n i18n = new CustomClaimI18n(language);
            i18n.label = label;
            i18n.content = content;
            return i18n;
        }
    }
}
