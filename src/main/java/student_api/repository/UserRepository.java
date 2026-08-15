package student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
