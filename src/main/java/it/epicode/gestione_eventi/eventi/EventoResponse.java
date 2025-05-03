package it.epicode.gestione_eventi.eventi;



import java.time.LocalDateTime;

public record EventoResponse(
        Long id,
        String titolo,
        LocalDateTime dataOra,
        String luogo,
        int postiDisponibili
) {}