package vn.binh.springbootsproject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.binh.springbootsproject.entity.UserInfo;
import vn.binh.springbootsproject.repository.UserInfoRepository;

@Service
public record UserService (UserInfoRepository repository, PasswordEncoder passwordEncoder)
{
    public String addUser(UserInfo user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "User added successfully";
    }
}
