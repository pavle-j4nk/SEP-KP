package rs.ac.uns.ftn.sep.kp.service;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.constants.PaymentMethod;

@Component
public class BankPaymentProcessor extends RemotePaymentProcessor {
    public BankPaymentProcessor(RestTemplate restTemplate, LoadBalancerClient loadBalancer) {
        super(restTemplate, loadBalancer);
    }

    @Override
    boolean canProcess(String paymentProcessorName) {
        return PaymentMethod.BANK.toString().equalsIgnoreCase(paymentProcessorName);
    }

    @Override
    String getServiceDiscoveryId() {
        return PaymentMethod.BANK.toString();
    }
}
