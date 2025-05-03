package it.epicode.gestione_eventi.prenotazioni;


import it.epicode.gestione_eventi.common.CommonResponse;
import it.epicode.gestione_eventi.eventi.Evento;
import it.epicode.gestione_eventi.eventi.EventoRepository;
import it.epicode.gestione_eventi.exceptions.NotFoundException;
import it.epicode.gestione_eventi.utenti.Utente;
import it.epicode.gestione_eventi.utenti.UtenteRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public CommonResponse creaPrenotazione(PrenotazioneRequest request, Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));

        if(prenotazioneRepository.existsByEventoAndUtente(evento, utente)) {
            throw new IllegalArgumentException("Prenotazione già esistente per questo evento");
        }

        if(evento.getPostiDisponibili() < request.getNumeroPosti()) {
            throw new IllegalArgumentException("Posti insufficienti");
        }



        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazione.setNumeroPosti(request.getNumeroPosti());
        prenotazione.setDataPrenotazione(LocalDateTime.now());
        prenotazione.setNoteUtente(request.getNoteUtente());

        // Aggiorna posti disponibili
        evento.setPostiDisponibili(evento.getPostiDisponibili() - request.getNumeroPosti());
        eventoRepository.save(evento);

        Prenotazione saved = prenotazioneRepository.save(prenotazione);
        return new CommonResponse(saved.getId());
    }

    public Page<Prenotazione> getAllPrenotazioni(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return prenotazioneRepository.findAll(pageable);
    }
    public Prenotazione getPrenotazioneById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
    }

    public List<Prenotazione> findByUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public void eliminaPrenotazione(Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new NotFoundException("Prenotazione non trovata");
        }
        prenotazioneRepository.deleteById(id);
    }

    public void aggiornaPrenotazione(Long id, PrenotazioneRequest request) {
        Prenotazione prenotazione = getPrenotazioneById(id);
        prenotazione.setNumeroPosti(request.getNumeroPosti());
        prenotazione.setNoteUtente(request.getNoteUtente());
        prenotazioneRepository.save(prenotazione);
    }
}
