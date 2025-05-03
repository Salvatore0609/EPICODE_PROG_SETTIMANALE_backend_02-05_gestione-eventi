package it.epicode.gestione_eventi.eventi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findById(Long id);
    List<Evento> findByOrganizzatoreId(Long organizzatoreId);
    List<Evento> findByPostiDisponibiliGreaterThan(int posti);
}