package com.hbzb.cloud.tender.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * implements ResponseErrorHandler for catch
 * restTemplate exception
 * created by dusizhong at 2020.01.16
 */
public class RestTemplateException implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
    }
}
