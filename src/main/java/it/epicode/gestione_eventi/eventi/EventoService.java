package it.epicode.gestione_eventi.eventi;

import it.epicode.gestione_eventi.exceptions.NotFoundException;
import it.epicode.gestione_eventi.utenti.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;

    public Evento getEventoById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
    }

    public EventoResponse creaEvento(EventoRequest request, Utente organizzatore) {
        Evento evento = new Evento();
        evento.setTitolo(request.titolo());
        evento.setDescrizione(request.descrizione());
        evento.setDataOra(request.dataOra());
        evento.setLuogo(request.luogo());
        evento.setPostiTotali(request.postiTotali());
        evento.setPostiDisponibili(request.postiTotali());
        evento.setOrganizzatore(organizzatore);

        eventoRepository.save(evento);

        return new EventoResponse(
                evento.getId(),
                evento.getTitolo(),
                evento.getDataOra(),
                evento.getLuogo(),
                evento.getPostiDisponibili()
        );
    }
}