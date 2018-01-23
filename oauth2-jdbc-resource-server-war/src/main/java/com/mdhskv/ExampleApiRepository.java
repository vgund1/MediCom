package com.mdhskv;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

/**
 * Created by quynh on 11/18/2017.
 */

@RestController
@RequestMapping(path = "/api")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ExampleApiRepository {

    private String message = "Hello world!";

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map<String, String> home() {
        return Collections.singletonMap("message", message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void updateMessage(@RequestBody String message) {
        this.message = message;
    }

    @PreAuthorize("#oauth2.hasScope('api3') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, String> user(Principal user) {
        return Collections.singletonMap("message", "user is: " + user.toString());
    }
}
