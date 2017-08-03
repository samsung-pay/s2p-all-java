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

package com.samsungpay.s2p.membership;

import com.fasterxml.jackson.annotation.*;
import com.samsungpay.s2p.common.AbstractRegistration;
import com.samsungpay.s2p.membership.model.*;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Registration extends AbstractRegistration {

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonProperty("cardId")
    private String cardId;

    @JsonProperty("membershipId")
    private String membershipId;

    @JsonProperty("membershipIdType")
    private MembershipIDType membershipIdType;

    @JsonProperty("programName")
    private String programName;

    @JsonProperty("barcode")
    private Barcode barcode;

    @JsonProperty("tracks")
    private Tracks tracks;

    @JsonProperty("cardStatus")
    private CardStatus cardStatus;

    @JsonProperty("userMessages")
    private List<UserMessage> userMessages;

    @JsonProperty("customClaims")
    private List<CustomClaim> customClaims;

    @JsonProperty("cardArt")
    private CardArt cardArt;

    private Registration() {
    }

    public String getCardId() { return cardId; }

    public String getMembershipId() {
        return membershipId;
    }

    public MembershipIDType getMembershipIdType() {
        return membershipIdType;
    }

    public String getProgramName() {
        return programName;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public List<UserMessage> getUserMessages() {
        return userMessages;
    }

    public List<CustomClaim> getCustomClaims() {
        return customClaims;
    }

    public CardArt getCardArt() { return cardArt; }

    public static final class Builder {
        private String cardId;
        private String membershipId;
        private MembershipIDType membershipIdType;
        private String programName;
        private Barcode barcode;
        private Tracks tracks;
        private CardStatus cardStatus;
        private List<UserMessage> userMessages;
        private List<CustomClaim> customClaims;
        private CardArt cardArt;

        private Builder() {
        }

        public Builder setCardId(String cardId) {
            this.cardId = cardId;
            return this;
        }

        public Builder setMembershipId(String membershipId) {
            this.membershipId = membershipId;
            return this;
        }

        public Builder setMembershipIDType(MembershipIDType membershipIdType) {
            this.membershipIdType = membershipIdType;
            return this;
        }

        public Builder setProgramName(String programName) {
            this.programName = programName;
            return this;
        }

        public Builder setBarcode(Barcode barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder setTracks(Tracks tracks) {
            this.tracks = tracks;
            return this;
        }

        public Builder setCardStatus(CardStatus cardStatus) {
            this.cardStatus = cardStatus;
            return this;
        }

        public Builder setUserMessages(List<UserMessage> userMessages) {
            this.userMessages = userMessages;
            return this;
        }

        public Builder setCustomClaims(List<CustomClaim> customClaims) {
            this.customClaims = customClaims;
            return this;
        }

        public Builder setCardArt(CardArt cardArt) {
            this.cardArt = cardArt;
            return this;
        }

        public Registration build() throws IllegalArgumentException {
            if (cardId == null || cardId.isEmpty())
                throw new IllegalArgumentException("Registration card ID is missing");
            if (membershipId == null || membershipId.isEmpty())
                throw new IllegalArgumentException("Registration membership ID is missing");
            if (membershipIdType == null)
                throw new IllegalArgumentException("Registration membership ID type is missing");
            if (programName == null || programName.isEmpty())
                throw new IllegalArgumentException("Registration programName is missing");

            Registration registration = new Registration();
            registration.cardId = cardId;
            registration.membershipId = membershipId;
            registration.membershipIdType = membershipIdType;
            registration.programName = programName;
            registration.barcode = barcode;
            registration.tracks = tracks;
            registration.cardStatus = cardStatus;
            registration.userMessages = userMessages;
            registration.customClaims = customClaims;
            registration.cardArt = cardArt;
            return registration;
        }
    }

    public enum CardStatus {
        INACTIVE,
        ACTIVE
    }
}
