package it.epicode.gestione_eventi.utenti;

import com.github.javafaker.Faker;
import it.epicode.gestione_eventi.auth.app_user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UtentiRunner implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private Faker faker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (utenteRepository.count() == 0) {
            try {
                List<Utente> utenti = new ArrayList<>();

                // Crea 10 Utenti normali
                for (int i = 0; i < 10; i++) {
                    Utente utente = new Utente();
                    utente.setUsername(faker.name().username() + i); // Username univoco
                    utente.setPassword(passwordEncoder.encode("Password123!"));
                    utente.setRuolo(Role.UTENTE);

                    utenti.add(utente);
                }

                for (int i = 0; i < 5; i++) {
                    Utente organizzatore = new Utente();
                    organizzatore.setUsername(faker.name().username());
                    organizzatore.setPassword(passwordEncoder.encode("Organizzatore123!"));
                    organizzatore.setRuolo(Role.ORGANIZZATORE);


                    utenti.add(organizzatore);
                }


                // Salva tutti gli utenti
                utenteRepository.saveAll(utenti);
                log.info(" utenti creati con successo", utenti.size());

            } catch (Exception e) {
                log.error(" Errore durante la creazione degli utenti: ", e.getMessage());
            }
        } else {
            log.info("Il database contiene già utenti, skip della creazione iniziale");
        }
    }
}