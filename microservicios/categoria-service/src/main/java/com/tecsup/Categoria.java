package com.tecsup;
import jakarta.persistence.*; import jakarta.validation.constraints.NotBlank;
@Entity public class Categoria { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @NotBlank public String nombre; @NotBlank public String estado; }
