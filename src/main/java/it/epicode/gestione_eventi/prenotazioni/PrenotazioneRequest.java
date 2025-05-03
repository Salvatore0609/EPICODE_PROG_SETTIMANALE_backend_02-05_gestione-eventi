package it.epicode.gestione_eventi.prenotazioni;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {

    @NotNull(message = "L'ID evento è obbligatorio")
    private Long eventoId;

    @NotNull(message = "Il numero di posti è obbligatorio")
    private Integer numeroPosti;

    private String noteUtente;
}
