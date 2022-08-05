package com.copterbuddy.prototype.backend;

import com.copterbuddy.prototype.backend.entity.Address;
import com.copterbuddy.prototype.backend.entity.Social;
import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.service.AddressService;
import com.copterbuddy.prototype.backend.service.SocialService;
import com.copterbuddy.prototype.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

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
        Assertions.assertEquals(TestCreateData.email, user.getEmail());
        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
        Assertions.assertTrue(isMatched);
        Assertions.assertEquals(TestCreateData.name, user.getName());
    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        User updatedUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(TestUpdateData.name, updatedUser.getName());
    }

    @Order(3)
    @Test
    void testCreateSocial() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.create(user,
                SocialTestCreateData.facebook,
                SocialTestCreateData.line,
                SocialTestCreateData.instagram,
                SocialTestCreateData.tiktok);

        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());
    }

    @Order(4)
    @Test
    void testCreateAddress() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());

        createAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        createAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);

    }

    private void createAddress(User user, String line1, String line2, String zipcode) {

        Address address = addressService.create(
                user,
                line1,
                line2,
                zipcode
        );

        Assertions.assertNotNull(address);
        Assertions.assertEquals(line1, address.getLine1());
        Assertions.assertEquals(line2, address.getLine2());
        Assertions.assertEquals(zipcode, address.getZipcode());

    }

    @Order(9)
    @Test
    void testDelete() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        userService.deleteById(user.getId());

        //check social
        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());

        //check address
        List<Address> addresses = user.getAddresses();
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertEquals(2,addresses.size());

        userService.deleteById(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(optDelete.isEmpty());
    }

    interface AddressTestCreateData {
        String line1 = "123/4";
        String line2 = "Muang";
        String zipcode = "10110";
    }

    interface AddressTestCreateData2 {
        String line1 = "123/4";
        String line2 = "Muang";
        String zipcode = "10110";
    }

    interface TestCreateData {
        String email = "cop@test.com";
        String password = "1234";
        String name = "cop";

    }

    interface TestUpdateData {
        String name = "cop edit";
    }

    interface SocialTestCreateData {
        String facebook = "copterbuddy";
        String line = "copternp";
        String instagram = "copternp";
        String tiktok = "copternp";
    }
}
