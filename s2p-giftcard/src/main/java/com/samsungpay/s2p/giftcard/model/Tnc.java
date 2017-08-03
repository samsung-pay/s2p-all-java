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

public class Tnc {

    private String url;
    private String content;

    private Tnc() {}

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String content;

        private Builder() {}

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Tnc build() throws IllegalArgumentException {
            boolean noContent = content == null || content.isEmpty();
            boolean noUrl = url == null || url.isEmpty();
            if (noContent && noUrl)
                throw new IllegalArgumentException("Terms and Conditions' content and url are both missing, at least one of them has to be provided");

            Tnc tnc = new Tnc();
            tnc.content = content;
            tnc.url = url;
            return tnc;
        }
    }
}
