package auth.infrastructure.repository;

import auth.domain.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Integer> {

}
