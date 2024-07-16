package aluracursos.forohub_challenge.domain.topics;

import java.time.LocalDateTime;

public record TopicResponseData(
        Long id,
        String titulo,
        String mensaje,
        State status,
        String autores,
        String nombreCurso,
        LocalDateTime fecha
) {
}
