package rs.ac.uns.ftn.sep.kp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusRequest;
import rs.ac.uns.ftn.sep.commons.dto.PaymentStatusResponse;
import rs.ac.uns.ftn.sep.commons.helper.ResponseEntityHelper;
import rs.ac.uns.ftn.sep.kp.dto.*;
import rs.ac.uns.ftn.sep.kp.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;

    @PostMapping
    public InitializePaymentResponse initializePayment(@RequestBody InitializePaymentRequest request) {
        return paymentService.initializePayment(request);
    }

    @GetMapping("{paymentId}")
    @CrossOrigin(origins = {"http://localhost:3000", "https://localhost:3000"})
    public PaymentExecutionInfo getPaymentExecutionInfo(@PathVariable Long paymentId) {
        return paymentService.getPaymentExecutionInfo(paymentId);
    }

    @GetMapping("/{paymentId}/execute/{processor}")
    public ResponseEntity<?> execute(@PathVariable String processor, @PathVariable Long paymentId) {
        ExecutePaymentResponse execute = paymentService.execute(processor, paymentId);
        return ResponseEntityHelper.sendRedirect(execute.getRedirectUrl());
    }

    @GetMapping("/{paymentId}/status")
    public PaymentStatusResponse getPaymentStatus(@PathVariable Long paymentId, PaymentStatusRequest request) {
        return paymentService.getPaymentStatus(request.withPaymentId(paymentId));
    }

}
