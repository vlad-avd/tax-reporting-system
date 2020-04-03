package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;
import ua.kpi.iasa.taxreportingsystem.dto.UserDTO;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public User createUser(UserDTO userDTO){
        return userRepo.save(User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Collections.singleton(Role.ROLE_USER))
                .active(true).build());
    }
}
