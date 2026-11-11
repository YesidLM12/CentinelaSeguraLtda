package org.yesidl12.centinelaseguraltda.dto;

public record Clientecommand (
    String tipo,
    String documento,
    String nombreRazonSocial,
    String representanteLegal,
    String correo,
    String telefono,
    String direccion,
    String ciudad,
    String observaciones){}
