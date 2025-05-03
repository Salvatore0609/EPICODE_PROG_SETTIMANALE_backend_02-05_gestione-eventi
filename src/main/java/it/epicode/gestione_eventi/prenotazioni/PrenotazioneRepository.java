package it.epicode.gestione_eventi.prenotazioni;


import it.epicode.gestione_eventi.eventi.Evento;
import it.epicode.gestione_eventi.utenti.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByEventoAndUtente(Evento evento, Utente utente);
    boolean existsByEventoDataOraAndUtenteId(LocalDateTime dataOra, Long utenteId);
    List<Prenotazione> findByUtente(Utente utente);
}