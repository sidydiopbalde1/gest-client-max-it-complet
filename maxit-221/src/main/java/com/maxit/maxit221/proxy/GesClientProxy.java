package com.maxit.maxit221.proxy;

import com.maxit.maxit221.dto.CitoyenDto;
import com.maxit.maxit221.dto.GesClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="ges-client",url = "http://localhost:3000")
public interface GesClientProxy {
    @GetMapping("/api/clients/client/{numero}")
    @ResponseBody
    GesClientDto findClientByNumero(@PathVariable String numero);

}
