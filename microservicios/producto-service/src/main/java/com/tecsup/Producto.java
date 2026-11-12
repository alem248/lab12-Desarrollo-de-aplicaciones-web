package com.tecsup;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity public class Producto { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @NotBlank public String nombre; @NotNull @Positive public Double precio; @Min(0) public int stock; @NotNull public Long categoriaId; @Transient public CategoriaDTO categoria; }
