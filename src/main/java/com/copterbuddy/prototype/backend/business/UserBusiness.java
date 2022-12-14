package com.copterbuddy.prototype.backend.business;

import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.FileException;
import com.copterbuddy.prototype.backend.exeption.UserException;
import com.copterbuddy.prototype.backend.mapper.UserMapper;
import com.copterbuddy.prototype.backend.model.MLoginRequest;
import com.copterbuddy.prototype.backend.model.MLoginResponse;
import com.copterbuddy.prototype.backend.model.MRegisterRequest;
import com.copterbuddy.prototype.backend.model.MRegisterResponse;
import com.copterbuddy.prototype.backend.service.TokenService;
import com.copterbuddy.prototype.backend.service.UserService;
import com.copterbuddy.prototype.backend.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    public MLoginResponse login(MLoginRequest request) throws UserException {
        //validate request

        //verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            // throw login fail,password incorrect
            throw UserException.loginFailPasswordIncorrect();
        }

        // * Generate JWT
        String token = tokenService.tokenize(user);

        MLoginResponse response = new MLoginResponse();
        response.setToken(token);

        return response;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()){
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());

        // mapper
        return userMapper.toRegisterResponse(user);
    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        //validate file
        if (file == null) {
            //throw error
            throw FileException.fileNull();
        }

        //validate size
        if (file.getSize() > 1048576 * 2) {
            //throw error
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            //throw error
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            //throw error (unsupport)
            throw FileException.unsupported();
        }

        // TODO: upload file File Storage (AWS S3,etc...)
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
