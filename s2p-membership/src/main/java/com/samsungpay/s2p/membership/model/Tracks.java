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

public class Tracks {
    private String track1;
    private String track2;
    private String track3;

    private Tracks() {
    }

    public String getTrack1() {
        return track1;
    }

    public String getTrack2() {
        return track2;
    }

    public String getTrack3() {
        return track3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String track1;
        private String track2;
        private String track3;

        private Builder() {
        }

        public Builder setTrack1(String track1) {
            this.track1 = track1;
            return this;
        }

        public Builder setTrack2(String track2) {
            this.track2 = track2;
            return this;
        }

        public Builder setTrack3(String track3) {
            this.track3 = track3;
            return this;
        }

        public Tracks build() throws IllegalArgumentException {
            if ((track1 == null || track1.isEmpty())
                    && (track2 == null || track2.isEmpty())
                    && (track3 == null || track3.isEmpty()))
                throw new IllegalArgumentException("Track (track1, track2, track3) is missing");

            Tracks tracks = new Tracks();
            tracks.track1 = this.track1;
            tracks.track2 = this.track2;
            tracks.track3 = this.track3;
            return tracks;
        }
    }
}
