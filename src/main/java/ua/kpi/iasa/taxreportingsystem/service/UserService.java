package ua.kpi.iasa.taxreportingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.taxreportingsystem.domain.User;
import ua.kpi.iasa.taxreportingsystem.domain.enums.Role;
import ua.kpi.iasa.taxreportingsystem.dto.UserDto;
import ua.kpi.iasa.taxreportingsystem.repos.UserRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + "is not found"));
    }

    public Page<User> getAllUsers(Pageable pageable){
        return userRepo.findAll(pageable);
    }

    public Optional<User> findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public void createUser(User user){
        userRepo.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(Collections.singleton(Role.ROLE_USER))
                .active(true)
                .build());
    }

    public void editUser(User user, UserDto editedUser) {

        user.setUsername(editedUser.getUsername());
        user.setPassword(editedUser.getPassword());
        user.getRoles().clear();

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for (String key : editedUser.getRoleCheckboxFlag().keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        saveUser(user);
    }
}
