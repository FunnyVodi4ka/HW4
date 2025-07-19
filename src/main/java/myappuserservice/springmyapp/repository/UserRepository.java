package myappuserservice.springmyapp.repository;

import myappuserservice.springmyapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
