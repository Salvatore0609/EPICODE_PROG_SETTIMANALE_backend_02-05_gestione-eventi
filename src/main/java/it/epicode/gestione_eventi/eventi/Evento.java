package it.epicode.gestione_eventi.eventi;

import it.epicode.gestione_eventi.prenotazioni.Prenotazione;
import it.epicode.gestione_eventi.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "eventi")
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @Column(nullable = false)
    private LocalDateTime dataOra;

    @Column(nullable = false)
    private String luogo;

    @Column(nullable = false)
    private Integer postiTotali;

    @Column(nullable = false)
    private Integer postiDisponibili;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizzatore_id", nullable = false)
    private Utente organizzatore;
}
