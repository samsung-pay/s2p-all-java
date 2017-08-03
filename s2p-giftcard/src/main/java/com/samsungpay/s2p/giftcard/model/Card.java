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

public class Card {
    private String id;
    private String imageUrl;
    private Tnc tnc;

    private Card() {}

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Tnc getTnc() {
        return tnc;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String imageUrl;
        private Tnc tnc;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder tnc(Tnc tnc) {
            this.tnc = tnc;
            return this;
        }

        public Card build() throws IllegalArgumentException {
            if (id == null || id.isEmpty())
                throw new IllegalArgumentException("Card id is missing");
            if (imageUrl == null || imageUrl.isEmpty())
                throw new IllegalArgumentException("Card image url is missing");
            if (tnc == null)
                throw new IllegalArgumentException("Card terms and conditions is missing");

            Card card = new Card();
            card.id = id;
            card.imageUrl = imageUrl;
            card.tnc = tnc;
            return card;
        }
    }
}
