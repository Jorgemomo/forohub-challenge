package aluracursos.forohub_challenge.domain.topics;

import java.time.LocalDateTime;

public record TopicListData(Long id, String titulo, String mensaje, State status, String autores, String nombreCurso,
                            LocalDateTime fecha) {
    public TopicListData(Topic topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getStatus(), topico.getAutores().getUsername(), topico.getNombreCurso(), topico.getFecha());
    }
}
