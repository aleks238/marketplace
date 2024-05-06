package com.webmarket.microservices.auth.services;

import com.webmarket.microservices.auth.dtos.UserDto;
import com.webmarket.microservices.auth.entities.Role;
import com.webmarket.microservices.auth.entities.User;
import com.webmarket.microservices.auth.exceptionHandling.Exceptions.RegistrationException;
import com.webmarket.microservices.auth.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found ", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void createNewUser(UserDto userDto){
        isUserExist(userDto.getUsername());
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(roles);
        save(user);
    }

    private void isUserExist(String name){
        if (findByUsername(name).isPresent()){//заменить на userRepository.exists()
            throw new RegistrationException("Пользователь с таким именем уже зарегистрирован");
        }
    }
}
