package com.copterbuddy.prototype.backend;

import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.UserException;
import com.copterbuddy.prototype.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

	@Autowired
	private UserService userService;

	@Order(1)
	@Test
	void testCreate() throws BaseException {
		User user = userService.create(
				TestCreateData.email,
				TestCreateData.password,
				TestCreateData.name
		);

		//check not null
		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());

		//check equals
		Assertions.assertEquals(TestCreateData.email,user.getEmail());
		boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
		Assertions.assertTrue(isMatched);
		Assertions.assertEquals(TestCreateData.name,user.getName());
	}

	@Order(2)
	@Test
	void testUpdate() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		User updatedUser = userService.updateName(user.getId(), TestUpdateData.name);

		Assertions.assertNotNull(updatedUser);
		Assertions.assertEquals(TestUpdateData.name,updatedUser.getName());
	}

	@Order(3)
	@Test
	void testDelete() {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();
		userService.delete(user.getId());

		Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(optDelete.isEmpty());
	}

	interface TestCreateData {
		String email = "cop@test.com";
		String password = "1234";
		String name = "cop";

	}

	interface TestUpdateData {
		String name = "cop edit";

	}

}