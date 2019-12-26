package rs.ac.uns.ftn.sep.kp.service;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.constants.PaymentMethod;

@Component
public class PaypalRemotePaymentProcessor extends RemotePaymentProcessor {
    public PaypalRemotePaymentProcessor(RestTemplate restTemplate, LoadBalancerClient loadBalancer) {
        super(restTemplate, loadBalancer);
    }

    @Override
    boolean canProcess(String paymentProcessorName) {
        return PaymentMethod.PAYPAL.toString().equalsIgnoreCase(paymentProcessorName);
    }

    @Override
    String getServiceDiscoveryId() {
        return PaymentMethod.PAYPAL.getValue();
    }
}
