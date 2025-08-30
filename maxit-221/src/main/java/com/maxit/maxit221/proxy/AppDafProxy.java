package com.maxit.maxit221.proxy;

import com.maxit.maxit221.dto.CitoyenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="app-daf",url = "http://localhost:4000")
public interface AppDafProxy {
    @GetMapping("/api/citoyens/get/{cni}")
    @ResponseBody
    CitoyenDto findCitoyenByNci(@PathVariable String cni);

}
