package com.logistic.rms.service;

import com.logistic.rms.dao.IUserDao;
import com.logistic.rms.daoEntities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    IUserDao iUserDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Validatinf the credential for Usename : '{}'",s);
        User user = iUserDao.getUsername(s);
        if(user == null){
            log.error("No User found wrt username '{}'",s);
            throw new UsernameNotFoundException("No User found");
        }
        else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            user.getUserRoleList().forEach(userRole -> {
                grantedAuthorities.add(userRole.getRole());
            });
            log.debug("User Found . Building user builder : {}" ,user.toString());
            return org.springframework.security.core.userdetails.User.builder()
                    .disabled(!user.getEnabled())
                    .credentialsExpired(!user.getCredentialsNonExpired())
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .authorities(grantedAuthorities)
                    .accountLocked(!user.getAccountNonLocked())
                    .accountExpired(!user.getAccountNonExpired())
                    .build();
        }
    }
}
