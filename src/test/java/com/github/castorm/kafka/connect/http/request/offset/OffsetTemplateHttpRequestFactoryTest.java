package com.github.castorm.kafka.connect.http.request.offset;

/*-
 * #%L
 * kafka-connect-http-plugin
 * %%
 * Copyright (C) 2020 CastorM
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.github.castorm.kafka.connect.http.model.Offset;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.castorm.kafka.connect.http.model.HttpRequest.HttpMethod.POST;
import static com.github.castorm.kafka.connect.http.request.offset.OffsetTemplateHttpRequestFactoryTest.Fixture.offset;
import static com.github.castorm.kafka.connect.http.request.offset.OffsetTemplateHttpRequestFactoryTest.Fixture.url;
import static com.github.castorm.kafka.connect.http.request.offset.OffsetTemplateHttpRequestFactoryTest.Fixture.value;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class OffsetTemplateHttpRequestFactoryTest {

    OffsetTemplateHttpRequestFactory factory;

    @BeforeEach
    void setUp() {
        factory = new OffsetTemplateHttpRequestFactory();
    }

    @Test
    void givenMethod_whenCreateRequest_thenMethodMapped() {

        given("http.request.method", "POST");

        assertThat(factory.createRequest(offset).getMethod()).isEqualTo(POST);
    }

    @Test
    void givenUrl_whenCreateRequest_thenMethodMapped() {

        factory.configure(ImmutableMap.of("http.request.url", url));

        assertThat(factory.createRequest(offset).getUrl()).isEqualTo(url);
    }

    @Test
    void givenHeaders_whenCreateRequest_thenHeadersMapped() {

        given("http.request.headers", "Header:Value");

        assertThat(factory.createRequest(offset).getHeaders()).containsEntry("Header", singletonList("Value"));
    }

    @Test
    void givenQueryParams_whenCreateRequest_thenQueryParamsMapped() {

        given("http.request.params", "param=value");

        assertThat(factory.createRequest(offset).getQueryParams()).containsEntry("param", singletonList("value"));
    }

    @Test
    void givenBody_whenCreateRequest_thenBodyMapped() {

        given("http.request.body", value);

        assertThat(factory.createRequest(offset).getBody()).isEqualTo(value.getBytes());
    }

    private void given(String key, String value) {
        factory.configure(ImmutableMap.of("http.request.url", url, key, value));
    }

    interface Fixture {
        String value = "value";
        String url = "url";
        Offset offset = Offset.of(emptyMap());
    }
}
