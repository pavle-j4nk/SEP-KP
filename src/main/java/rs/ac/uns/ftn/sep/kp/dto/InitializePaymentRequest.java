package rs.ac.uns.ftn.sep.kp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitializePaymentRequest {
    private Double amount;
    private String merchant;
    private String redirectUrl;
}
