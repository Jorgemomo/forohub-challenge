package aluracursos.forohub_challenge.domain.topics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicRepository extends JpaRepository<Topic,Long> {

    Boolean existsByTituloAndMensaje(String titulo,String mensaje);

    @Query("SELECT t FROM Topico t WHERE FUNCTION('YEAR', t.fecha) = :year")
    Page<Topic> findByFechaYear(@Param("year") int year, Pageable pageable);
}
