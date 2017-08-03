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

public class UserMessageI18n extends I18n<UserMessageI18n> {
    private String title;
    private String body;

    private UserMessageI18n(@JsonProperty("language") String language) {
        super(language);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String language;
        private String title;
        private String body;

        private Builder() {
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public UserMessageI18n build() throws IllegalArgumentException {
            UserMessageI18n i18n = new UserMessageI18n(language);
            i18n.title = title;
            i18n.body = body;
            return i18n;
        }
    }
}
