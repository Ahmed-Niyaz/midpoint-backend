package com.project.midpoint.service;

import com.project.midpoint.model.UserPrincipal;
import com.project.midpoint.model.Users;
import com.project.midpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        try {
            Users user = userRepository.findByUsername(username);

            if (user == null) {
                System.out.println("User is null - not found in database");
                throw new UsernameNotFoundException("No user found with username: " + username);
            }

            System.out.println("User found - creating UserPrincipal for username: " + username);
            return new UserPrincipal(user);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
