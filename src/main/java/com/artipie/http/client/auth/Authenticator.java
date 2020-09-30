/*
 * MIT License
 *
 * Copyright (c) 2020 Artipie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.http.client.auth;

import com.artipie.http.Headers;
import com.artipie.http.headers.Authorization;
import com.artipie.http.headers.WwwAuthenticate;

/**
 * Authenticator for HTTP requests.
 *
 * @since 0.3
 */
public interface Authenticator {

    /**
     * Get authorization headers.
     *
     * @param header WWW-Authenticate with requirements for authentication.
     * @return Authorization headers.
     */
    Headers headers(WwwAuthenticate header);

    /**
     * Basic authenticator for given username and password.
     *
     * @since 0.3
     */
    final class Basic implements Authenticator {

        /**
         * Username.
         */
        private final String username;

        /**
         * Password.
         */
        private final String password;

        /**
         * Ctor.
         *
         * @param username Username.
         * @param password Password.
         */
        public Basic(final String username, final String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public Headers headers(final WwwAuthenticate header) {
            final String scheme = header.scheme();
            if (!scheme.equals("Basic")) {
                throw new IllegalArgumentException(
                    String.format("Unsupported scheme '%s': %s", scheme, header.getValue())
                );
            }
            return new Headers.From(new Authorization.Basic(this.username, this.password));
        }
    }
}
