package it.epicode.gestione_eventi.utenti;

import it.epicode.gestione_eventi.auth.app_user.AppUserService;
import it.epicode.gestione_eventi.auth.app_user.Role;
import it.epicode.gestione_eventi.auth.authorization.AuthResponse;
import it.epicode.gestione_eventi.auth.authorization.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/utenti")
@RequiredArgsConstructor
public class UtenteController{

    private final AppUserService appUserService;

    @PostMapping("/registrazione")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registraUtente(@RequestBody UtenteAuthRequest request) {
        appUserService.registerUser(
                request.username(),
                request.password(),
                Set.of(Role.UTENTE) // Ruolo di default
        );
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

    @PostMapping("/auth/registrazione-organizzatore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> registraOrganizzatore(@RequestBody UtenteAuthRequest request) {
        appUserService.registerUser(
                request.username(),
                request.password(),
                Set.of(Role.ORGANIZZATORE)
        );
        return ResponseEntity.ok("Organizzatore registrato");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = appUserService.authenticateUser(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(new AuthResponse(token));
    }
}