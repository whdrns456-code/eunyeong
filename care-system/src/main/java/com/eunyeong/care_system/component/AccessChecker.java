package com.eunyeong.care_system.component;

import com.eunyeong.care_system.service.SeniorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("accessCheck")
@RequiredArgsConstructor
public class AccessChecker {

    private final SeniorService sService;

    public boolean hasSenior(Authentication authentication){

            if(authentication == null || !authentication.isAuthenticated()){
                return false;

            }
            String userId = authentication.getName();

            return sService.hasRegisteredSenior(userId);
    }

}
