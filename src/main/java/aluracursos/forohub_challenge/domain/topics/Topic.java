package aluracursos.forohub_challenge.domain.topics;

import aluracursos.forohub_challenge.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private State status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="autores_id")
    private User autores;

    private String nombreCurso;

    public Topic(
            String titulo,
            String mensaje,
            State status,
            User autores,
            String nombreCurso
    ){
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.status = status;
        this.autores = autores;
        this.nombreCurso = nombreCurso;
        this.fecha=LocalDateTime.now();

    }

    public void actualizarDatos(TopicUpdateData datosActualizarTopico) {
        if(datosActualizarTopico.titulo()!=null){
            this.titulo = datosActualizarTopico.titulo();
        }
        if(datosActualizarTopico.mensaje()!=null){
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if(datosActualizarTopico.nombreCurso()!=null){
            this.nombreCurso = datosActualizarTopico.nombreCurso();
        }
    }
}
