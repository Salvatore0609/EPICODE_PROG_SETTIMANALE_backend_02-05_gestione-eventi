package it.epicode.gestione_eventi.eventi;

import it.epicode.gestione_eventi.exceptions.NotFoundException;
import it.epicode.gestione_eventi.utenti.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventi")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public EventoResponse creaEvento(@RequestBody EventoRequest request, @AuthenticationPrincipal Utente organizzatore) {
        return eventoService.creaEvento(request, organizzatore);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public ResponseEntity<?> modificaEvento(
            @PathVariable Long id,
            @RequestBody EventoRequest request,
            @AuthenticationPrincipal Utente organizzatore
    ) {
        Evento evento = eventoService.getEventoById(id);
        if (!evento.getOrganizzatore().getId().equals(organizzatore.getId())) {
            throw new NotFoundException("Non sei l'organizzatore di questo evento");
        }
        // ... modifica
        return ResponseEntity.ok().build();
    }
}