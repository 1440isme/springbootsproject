package vn.binh.springbootsproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.binh.springbootsproject.entity.UserInfo;
import vn.binh.springbootsproject.repository.UserInfoRepository;

import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @GetMapping({ "/", "/home" })
    public String home(Authentication authentication, Model model) {
        // Lấy thông tin user hiện tại
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Kiểm tra role
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // Nếu là admin, hiển thị danh sách tất cả user
            List<UserInfo> users = userInfoRepository.findAll();
            model.addAttribute("users", users);
            model.addAttribute("currentUser", username);
            return "admin/users/list"; // Trang hiển thị danh sách user
        } else {
            // Nếu là user thường, hiển thị thông tin của user đó
            UserInfo currentUser = userInfoRepository.findByName(username)
                    .orElse(null);
            model.addAttribute("user", currentUser);
            return "web/user-info"; // Trang hiển thị thông tin user
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trang login
    }
}
