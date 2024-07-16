package aluracursos.forohub_challenge.domain.topics;

public record TopicUpdateData(
        Long id,
        String titulo,
        String mensaje,
        String nombreCurso
) {
}