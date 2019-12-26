package rs.ac.uns.ftn.sep.kp.service;

import rs.ac.uns.ftn.sep.commons.dto.SupportedPaymentMethods;

public interface SellerService {

    SupportedPaymentMethods getSupportedMethods(String merchant);

}
