package it.epicode.gestione_eventi.prenotazioni;

import it.epicode.gestione_eventi.eventi.Evento;
import it.epicode.gestione_eventi.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "prenotazioni")
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;    private LocalDateTime dataPrenotazione;
    private int numeroPosti;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @Column(columnDefinition = "TEXT")
    private String noteUtente;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}

