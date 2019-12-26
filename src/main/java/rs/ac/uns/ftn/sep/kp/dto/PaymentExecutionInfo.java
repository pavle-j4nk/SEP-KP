package rs.ac.uns.ftn.sep.kp.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentExecutionInfo {
    private Double amount;
    private List<PaymentExecutionMethod> methods;
}
