package com.wadhara.errorhandling;

public class Response<T> {

    private int httpStatusCode;
    private T responseBody;
    private Error error;

    public Response(int httpStatusCode, T responseBody, Error error) {
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
        this.error = error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "httpStatusCode=" + httpStatusCode +
                ", responseBody=" + responseBody +
                ", error=" + error +
                '}';
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
