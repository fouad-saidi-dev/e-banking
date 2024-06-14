package com.fouadev.backend.security.service;
/*
 Created by : Fouad SAIDI on 12/06/2024
 @author : Fouad SAIDI
 @date : 12/06/2024
 @project : e-banking
*/

import com.fouadev.backend.dtos.AppUserDTO;
import com.fouadev.backend.entities.AppUser;
import com.fouadev.backend.mappers.BankAccountMapperImpl;
import com.fouadev.backend.security.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private AppUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private BankAccountMapperImpl mapper;
    @Override
    public AppUserDTO loadUserByUsername(String username) {
        AppUser user = userRepository.findByUsername(username);
        AppUserDTO userDTO = mapper.fromAppUser(user);
        return userDTO;
    }
}