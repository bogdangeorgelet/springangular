package com.example.springangularapp.service;


import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) {
        CompanyEntity user = companyRepository.findByEmail(s);
        if (user == null)
            throw new UsernameNotFoundException("No user found with username: " + s);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user1 = new User(user.getEmail(), user.getPassword(), grantedAuthorities);
        return user1;
    }
}
