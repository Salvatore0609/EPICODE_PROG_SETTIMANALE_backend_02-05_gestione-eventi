// PrenotazioneController.java
package it.epicode.gestione_eventi.prenotazioni;

import it.epicode.gestione_eventi.common.CommonResponse;
import it.epicode.gestione_eventi.utenti.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Prenotazione> getAllPrenotazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return prenotazioneService.getAllPrenotazioni(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Prenotazione getPrenotazioneById(@PathVariable Long id) {
        return prenotazioneService.getPrenotazioneById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('UTENTE')")
    public List<Prenotazione> getMiePrenotazioni(@AuthenticationPrincipal Utente utente) {
        return prenotazioneService.findByUtente(utente);
    }

    @PostMapping("/utente/{utenteId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse creaPrenotazione(
            @RequestBody PrenotazioneRequest request,
            @PathVariable Long utenteId) {
        return prenotazioneService.creaPrenotazione(request, utenteId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaPrenotazione(@PathVariable Long id) {
        prenotazioneService.eliminaPrenotazione(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.OK)
    public void aggiornaPrenotazione(
            @PathVariable Long id,
            @RequestBody PrenotazioneRequest request) {
        prenotazioneService.aggiornaPrenotazione(id, request);
    }
}