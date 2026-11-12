package com.tecsup;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity public class Cliente { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @NotBlank public String nombre; @NotBlank public String apellido; @NotBlank public String direccion; @NotBlank public String estado; @NotBlank @Pattern(regexp="\\d{8}") public String nroDni; }
