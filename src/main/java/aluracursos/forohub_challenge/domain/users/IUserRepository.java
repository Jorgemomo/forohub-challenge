package aluracursos.forohub_challenge.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends JpaRepository<User,Long>{

    UserDetails findByEmail(String username);
}
