package com.example.demo.service;


import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

  @Autowired
  private AppUserRepository userRepository;

  @Autowired
  JwtUtil jwtUtil;


  public CustomResponse createUser(AppUser user) {

    if (userRepository.existsAppUserByUsername(user.getUsername())) {
      return new CustomResponse<>(null, CustomStatus.EXCEPTION, null);
    }

    userRepository.save(user);
    return new CustomResponse(null, CustomStatus.SUCCESS, null);
  }

  public CustomResponse loginUser(LoginRequest loginRequest) {
    AppUser user = userRepository.findAppUserByUsername(loginRequest.getUsername());
    if (user == null || user.getPassword().equals(loginRequest.getPassword())) {
      return new CustomResponse(null, CustomStatus.NOT_FOUND, null);
    }
    String token = jwtUtil.generateJwtToken(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    return new CustomResponse(null, CustomStatus.SUCCESS, token);
  }


}
