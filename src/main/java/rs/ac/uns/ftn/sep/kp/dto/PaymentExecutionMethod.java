package rs.ac.uns.ftn.sep.kp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.sep.commons.constants.PaymentMethod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentExecutionMethod {
    private PaymentMethod method;
    private String executeUrl;
}
