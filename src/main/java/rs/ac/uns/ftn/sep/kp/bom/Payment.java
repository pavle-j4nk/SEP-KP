package rs.ac.uns.ftn.sep.kp.bom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.sep.commons.constants.PaymentMethod;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long externalId;
    private String merchant;
    private String redirectUrl;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentProcessor;
    private String token;
}
