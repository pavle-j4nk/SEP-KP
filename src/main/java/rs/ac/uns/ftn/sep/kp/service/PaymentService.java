package rs.ac.uns.ftn.sep.kp.service;

import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusRequest;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusResponse;
import rs.ac.uns.ftn.sep.kp.dto.ExecutePaymentResponse;
import rs.ac.uns.ftn.sep.kp.dto.InitializePaymentRequest;
import rs.ac.uns.ftn.sep.kp.dto.InitializePaymentResponse;
import rs.ac.uns.ftn.sep.kp.dto.PaymentExecutionInfo;

public interface PaymentService {
    PaymentStatusResponse getPaymentStatus(PaymentStatusRequest request);

    PaymentExecutionInfo getPaymentExecutionInfo(Long id);

    InitializePaymentResponse initializePayment(InitializePaymentRequest request);

    ExecutePaymentResponse execute(String paymentProcessor, Long paymentId);
}
