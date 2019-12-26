package rs.ac.uns.ftn.sep.kp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.sep.commons.dto.SupportedPaymentMethods;
import rs.ac.uns.ftn.sep.commons.helper.UrlHelper;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RemoteSellerService implements SellerService {
    private final static String SERVICE_NAME = "seller";

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;

    @Override
    public SupportedPaymentMethods getSupportedMethods(String merchant) {
        URI uri = loadBalancerClient.choose(SERVICE_NAME).getUri();
        String requestUrl = UrlHelper.addPathVariables(uri.toString(), "api", "methods", merchant);
        return restTemplate.getForEntity(requestUrl, SupportedPaymentMethods.class).getBody();
    }

}
