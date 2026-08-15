package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student_api.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByTeacherId(String teacherId);
    Optional<User> findByEmail(String email);
}

