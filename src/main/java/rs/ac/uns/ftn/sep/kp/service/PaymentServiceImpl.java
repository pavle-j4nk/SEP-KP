package rs.ac.uns.ftn.sep.kp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.sep.commons.constants.PaymentMethod;
import rs.ac.uns.ftn.sep.commons.dto.*;
import rs.ac.uns.ftn.sep.commons.helper.UrlHelper;
import rs.ac.uns.ftn.sep.kp.bom.Payment;
import rs.ac.uns.ftn.sep.kp.bom.PaymentStatus;
import rs.ac.uns.ftn.sep.kp.dto.*;
import rs.ac.uns.ftn.sep.kp.exception.PaymentNotFound;
import rs.ac.uns.ftn.sep.kp.helper.TokenHelper;
import rs.ac.uns.ftn.sep.kp.properties.KpProperties;
import rs.ac.uns.ftn.sep.kp.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentServiceResolver paymentServiceResolver;
    private final SellerService sellerService;
    private final KpProperties properties;

    @Override
    public PaymentStatusResponse getPaymentStatus(PaymentStatusRequest request) {
        Payment localPayment = paymentRepository.findOneByIdAndToken(request.getPaymentId(), request.getToken())
                .orElseThrow(PaymentNotFound::new);

        PaymentStatusResponse paymentStatus = paymentServiceResolver.getByPaymentProcessorName(localPayment.getPaymentProcessor().toString())
                .getPaymentStatus(request.withPaymentId(localPayment.getExternalId()));
        return paymentStatus.withPaymentId(localPayment.getId());
    }

    @Override
    public PaymentExecutionInfo getPaymentExecutionInfo(Long id) {
        Payment payment = paymentRepository.getOne(id);

        SupportedPaymentMethods supportedMethods = sellerService.getSupportedMethods(payment.getMerchant());

        List<PaymentExecutionMethod> methods = supportedMethods.getSupportedPaymentMethods().stream()
                .map(pm -> new PaymentExecutionMethod(pm, buildExecutionUrl(pm, payment.getId())))
                .collect(Collectors.toList());
        PaymentExecutionInfo result = PaymentExecutionInfo.builder()
                .amount(payment.getAmount()).methods(methods).build();
        return result;
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentRequest request) {
        Payment localPayment = Payment.builder()
                .token(TokenHelper.generateRandomToken())
                .merchant(request.getMerchant())
                .amount(request.getAmount())
                .paymentStatus(PaymentStatus.INITIALIZED).build();

        localPayment = paymentRepository.save(localPayment);
        localPayment.setRedirectUrl(buildRedirectUrl(request.getRedirectUrl(), localPayment.getToken(), localPayment.getId()));
        localPayment = paymentRepository.save(localPayment);

        InitializePaymentResponse response = InitializePaymentResponse.builder().paymentId(localPayment.getId())
                .redirect(buildPaymentUrl(localPayment.getId())).build();
        return response;
    }

    @Override
    public ExecutePaymentResponse execute(String paymentProcessor, Long paymentId) {
        Payment localPayment = paymentRepository.getOne(paymentId);
        localPayment.setPaymentProcessor(PaymentMethod.forName(paymentProcessor));
        localPayment.setPaymentStatus(PaymentStatus.EXECUTED);
        paymentRepository.save(localPayment);

        CreatePaymentRequest paymentRequest =
                new CreatePaymentRequest(localPayment.getMerchant(), localPayment.getRedirectUrl(), localPayment.getAmount());
        CreatePaymentResponse remotePayment = paymentServiceResolver.getByPaymentProcessorName(paymentProcessor)
                .createPayment(paymentRequest);

        localPayment.setExternalId(remotePayment.getPaymentId());
        paymentRepository.save(localPayment);

        return new ExecutePaymentResponse(remotePayment.getRedirect());
    }

    private String buildRedirectUrl(String baseUrl, String token, Long paymentId) {
        baseUrl = UrlHelper.addQueryParam(baseUrl, "token", token);
        baseUrl = UrlHelper.addQueryParam(baseUrl, "paymentId", paymentId.toString());

        return baseUrl;
    }

    private String buildPaymentUrl(Long paymentId) {
        return UrlHelper.addPathVariables(properties.getUrl(), "payment", paymentId.toString());
    }

    private String buildExecutionUrl(PaymentMethod method, Long paymentId) {
        return UrlHelper.addPathVariables(properties.getUrl(), "api", "payment", paymentId.toString(),
                "execute", method.getValue());
    }

}
