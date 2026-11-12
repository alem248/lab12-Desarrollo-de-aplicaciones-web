package com.tecsup;
import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="categoria-service") public interface CategoriaClient { @GetMapping("/api/categorias/{id}") CategoriaDTO get(@PathVariable Long id); }
