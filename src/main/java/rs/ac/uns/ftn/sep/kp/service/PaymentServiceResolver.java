package rs.ac.uns.ftn.sep.kp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.sep.commons.service.BasePaymentService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentServiceResolver {
    private final ApplicationContext applicationContext;

    public BasePaymentService getByPaymentProcessorName(String name) {
        Map<String, RemotePaymentProcessor> processorBeans = applicationContext.getBeansOfType(RemotePaymentProcessor.class);
        return processorBeans.values().stream().filter(p -> p.canProcess(name)).findFirst().orElseGet(() -> null);
    }

}
