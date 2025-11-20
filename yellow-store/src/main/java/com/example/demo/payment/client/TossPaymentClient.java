package com.example.demo.payment.client;

import com.example.demo.payment.application.dto.PaymentCommand;
import com.example.demo.payment.client.dto.TossPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//@RequiredArgsConstructor
@Component
public class TossPaymentClient {

    private static final String CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Autowired
    private RestTemplate restTemplate;
//    private final TossPaymentProperties properties;
    @Value("${payment.toss.secret-key}")
    private String secretKey;

    public TossPaymentResponse confirm(PaymentCommand command) {
        if(secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("Toss secret key is not configured");
        }
        // 토스에 요청할 헤더
        HttpHeaders headers = createHeaders();

        // Body 객체화로 해도 상관없다.
//        Body body = new Body();
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", command.paymentKey());
        body.put("orderId", command.orderId());
        body.put("amount", command.amount());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            return restTemplate.postForObject(CONFIRM_URL, entity, TossPaymentResponse.class);
        } catch (HttpStatusCodeException ex) {
            HttpStatusCode statusCode = ex.getStatusCode();
            String responseBody = ex.getResponseBodyAsString();
            throw new IllegalStateException("Toss confirm failed (" + statusCode + "): " + responseBody, ex);
        }
    }

    /**
     * 토스에 전달하는 헤더<br>
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = secretKey + ":";
        //Base64 Encode
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        // 인코딩 값 입력
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
        return headers;
    }

//    public TossPaymentResponse confirm(PaymentCommand command) {
//        if (secretKey == null || secretKey.isBlank()) {
//            throw new IllegalStateException("Toss secret key is not configured");
//        }
//        HttpHeaders headers = createHeaders();
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("paymentKey", command.paymentKey());
//        body.put("orderId", command.orderId());
//        body.put("amount", command.amount());
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//
//        try {
//            return restTemplate.postForObject(CONFIRM_URL, entity, TossPaymentResponse.class);
//        } catch (HttpStatusCodeException ex) {
//            HttpStatusCode statusCode = ex.getStatusCode();
//            String responseBody = ex.getResponseBodyAsString();
//            throw new IllegalStateException("Toss confirm failed (" + statusCode + "): " + responseBody, ex);
//        }
//    }

    private class Body {
        String paymentKey;
        String orderId;
        long amount;
    }
}
