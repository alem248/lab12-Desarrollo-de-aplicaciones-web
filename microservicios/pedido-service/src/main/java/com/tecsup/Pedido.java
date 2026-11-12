package com.tecsup;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity public class Pedido { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @NotNull public Long clienteId; @NotNull public Long productoId; @Positive public int cantidad; @NotBlank public String estado="PENDIENTE"; }
