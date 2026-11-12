package com.tecsup;
public record PedidoResponse(Long id,int cantidad,String estado,ClienteDTO cliente,ProductoDTO producto) {}
