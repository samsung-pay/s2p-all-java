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

public class UserMessage {
    private String startAt;
    private String endAt;
    private List<UserMessageI18n> i18ns;

    private UserMessage() {
    }

    public String getStartAt() { return startAt; }
    public String getEndAt() { return  endAt; }
    public List<UserMessageI18n> getI18ns() {
        return i18ns;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String startAt;
        private String endAt;
        private List<UserMessageI18n> i18ns;

        private Builder() {
        }

        public Builder setStartAt(String startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder setEndAt(String endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder setI18ns(List<UserMessageI18n> i18ns) {
            this.i18ns = i18ns;
            return this;
        }

        public UserMessage build() throws IllegalArgumentException {
            if (Util.containsNullValue(i18ns))
                throw new IllegalArgumentException("UserMessage i18ns is missing");
            UserMessage userMessage = new UserMessage();
            userMessage.startAt = startAt;
            userMessage.endAt = endAt;
            userMessage.i18ns = i18ns;
            return userMessage;
        }
    }
}
