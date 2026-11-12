package com.tecsup;
import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="producto-service") public interface ProductoClient { @GetMapping("/api/productos/{id}") ProductoDTO get(@PathVariable Long id); }
