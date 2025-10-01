package vn.binh.springbootsproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.binh.springbootsproject.config.UserInfoUserDetals;
import vn.binh.springbootsproject.entity.UserInfo;
import vn.binh.springbootsproject.repository.UserInfoRepository;

import java.util.Optional;

public class UserInfoService implements UserDetailsService {
    @Autowired
    UserInfoRepository repository;
    public UserInfoService(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = repository.findByName(username);
        return user.map(UserInfoUserDetals::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            }
}
