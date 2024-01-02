package com.avensys.rts.formservice.APIClient;

import com.avensys.rts.formservice.customresponse.HttpResponse;
import com.avensys.rts.formservice.interceptor.JwtTokenInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${api.user.url}", configuration = JwtTokenInterceptor.class)
public interface UserAPIClient {

    @GetMapping("/{id}")
    HttpResponse getUserById(@PathVariable("id") Integer id);

    @GetMapping("/email/{email}")
    HttpResponse getUserByEmail(@PathVariable("email") String email);

}

