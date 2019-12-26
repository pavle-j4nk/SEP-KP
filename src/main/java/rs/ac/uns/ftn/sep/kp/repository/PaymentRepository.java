package rs.ac.uns.ftn.sep.kp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.sep.kp.bom.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findOneByIdAndToken(Long id, String token);

}
