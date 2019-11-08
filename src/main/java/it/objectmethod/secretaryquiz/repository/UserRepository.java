package it.objectmethod.secretaryquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.objectmethod.secretaryquiz.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
}
