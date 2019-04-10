package com.example.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Builder<T> builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private Integer code;
        private String message;
        private T data;
        private String success;
        private String fail;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder success() {
            this.code = 200;
            return this;
        }

        public Builder fail() {
            this.code = 500;
            return this;
        }

        public Result<T> build() {
            return new Result<>(this);
        }

    }
}
