package aluracursos.forohub_challenge.domain.topics;

import java.time.LocalDateTime;

public record TopicDetailsData(
        Long id,
        Long idUsuario,
        String titulo,
        String mensaje,
        String nombreCurso,
        LocalDateTime fecha
){
    public TopicDetailsData(Topic topico){
        this(topico.getId(), topico.getAutores().getId(), topico.getTitulo(), topico.getMensaje(), topico.getNombreCurso(),topico.getFecha());
    }
}
