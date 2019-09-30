/*
 * MIT License
 *
 * Copyright (c) 2019 Monbanquet.fr
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
package fr.monbanquet.sylph.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.monbanquet.sylph.exception.SylphException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class JacksonParser implements Parser {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
        objectMapper.registerModules(new JavaTimeModule());
    }

    @Override
    public <T> String serialize(T input) {
        try {
            return objectMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new SylphException(e);
        }
    }

    @Override
    public <T> T deserialize(String input, Class<T> returnType) {
        try {
            if (isEmpty(input)) return null;
            return objectMapper.readValue(input, returnType);
        } catch (IOException e) {
            throw new SylphException(e);
        }
    }

    @Override
    public <T> List<T> deserializeList(String input, Class<T> returnType) {
        try {
            if (isEmpty(input)) return null;
            JavaType valueType = TypeFactory.defaultInstance().constructParametricType(
                    List.class,
                    returnType);
            return objectMapper.readValue(input, valueType);
        } catch (IOException e) {
            throw new SylphException(e);
        }
    }

    private boolean isEmpty(String input) {
        return Objects.isNull(input) || "".equals(input.trim());
    }

}
