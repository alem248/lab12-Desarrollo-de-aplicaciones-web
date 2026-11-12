package com.tecsup;
import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="cliente-service") public interface ClienteClient { @GetMapping("/api/clientes/{id}") ClienteDTO get(@PathVariable Long id); }
