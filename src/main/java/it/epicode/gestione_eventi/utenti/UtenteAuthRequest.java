package it.epicode.gestione_eventi.utenti;

import jakarta.validation.constraints.NotBlank;

public record UtenteAuthRequest(
        @NotBlank String username,
        @NotBlank String password
) {}