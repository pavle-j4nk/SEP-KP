package rs.ac.uns.ftn.sep.kp.service;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.dto.CreatePaymentRequest;
import rs.ac.uns.ftn.sep.commons.dto.CreatePaymentResponse;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusRequest;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusResponse;
import rs.ac.uns.ftn.sep.commons.service.BasePaymentService;

import java.net.URI;

public abstract class RemotePaymentProcessor implements BasePaymentService {
    private static final String CREATE_PAYMENT_DEFAULT_URL = "/api/payment";
    private static final String PAYMENT_STATUS_DEFAULT_URL = "/api/payment/?paymentId={paymentId}&token={token}";

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancer;

    public RemotePaymentProcessor(RestTemplate restTemplate, LoadBalancerClient loadBalancer) {
        this.restTemplate = restTemplate;
        this.loadBalancer = loadBalancer;
    }

    @Override
    public PaymentStatusResponse getPaymentStatus(PaymentStatusRequest request) {
        URI uri = loadBalancer.choose(getServiceDiscoveryId()).getUri();
        return restTemplate.getForEntity(uri.toString() + PAYMENT_STATUS_DEFAULT_URL, PaymentStatusResponse.class, request.getPaymentId(), request.getToken()).getBody();
    }

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        URI uri = loadBalancer.choose(getServiceDiscoveryId()).getUri();
        return restTemplate.postForEntity(uri.toString() + CREATE_PAYMENT_DEFAULT_URL, request, CreatePaymentResponse.class).getBody();
    }

    abstract boolean canProcess(String paymentProcessorName);

    abstract String getServiceDiscoveryId();
}
