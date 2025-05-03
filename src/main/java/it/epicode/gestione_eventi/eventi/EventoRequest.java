package it.epicode.gestione_eventi.eventi;

import jakarta.validation.constraints.Future;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

public record EventoRequest(
        @NotBlank String titolo,
        String descrizione,
        @Future LocalDateTime dataOra,
        @NotBlank String luogo,
        @Positive int postiTotali
) {}