package com.mdhskv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by quynh on 11/12/2017.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CustomUserDetailService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
             return getUserCredential(username);
        }catch (Exception e){
            throw new UsernameNotFoundException(username);
        }
        // user = admin, password = 123456  (we must use BCryptPasswordEncoder()) for authentication
        //return new User("admin", "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public User getUserCredential(String username){

        String password;
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        String sql =    "select sec_user.username, sec_user.password, sec_user_role.role " +
                        "from sec_user, sec_user_role " +
                        "where sec_user.username = sec_user_role.username and sec_user.username = ? and sec_user.enabled = 1";

        List<Map<String, Object>> credentials = jdbcTemplate.queryForList(sql,username);

        if (credentials.isEmpty())
            return null;
        else{
            password = (String) credentials.get(0).get("password");
            for (Map<String, Object> credential : credentials){
                authorities.add(new SimpleGrantedAuthority((String) credential.get("role")));
            }
            return new User(username,password,authorities);
        }
    }
}
