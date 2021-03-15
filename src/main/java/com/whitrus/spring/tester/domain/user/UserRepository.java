package com.whitrus.spring.tester.domain.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.whitrus.spring.tester.domain.user.model.User;
import com.whitrus.spring.tester.domain.user.model.UserDTO;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(U.id, U.name, U.normalizedName, U.login) FROM User U")
	public Page<UserDTO> findAllUsersAsDTO(Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(U.id, U.name, U.normalizedName, U.login) FROM User U")
	public Page<UserDTO> findAllUsersWithDetailsAsDTO(Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(U.id, U.name, U.normalizedName, U.login) FROM User U WHERE U.id = :userId")
	public Optional<UserDTO> findUserByIdAsDTO(@Param("userId") Long userId);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(U.id, U.name, U.normalizedName, U.login) FROM User U WHERE U.id = :userId")
	public Optional<UserDTO> findUserWithDetailsByIdAsDTO(@Param("userId") Long userId);
}
