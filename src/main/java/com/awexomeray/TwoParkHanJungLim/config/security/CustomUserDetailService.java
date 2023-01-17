package com.awexomeray.TwoParkHanJungLim.config.security;

import com.awexomeray.TwoParkHanJungLim.dao.UserDao;
import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.getUser(username);
        return new PrincipalDetails(userEntity);
    }
}
