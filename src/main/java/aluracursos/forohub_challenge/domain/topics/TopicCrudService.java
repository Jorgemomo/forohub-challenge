package aluracursos.forohub_challenge.domain.topics;

import aluracursos.forohub_challenge.domain.topics.validate.ITopicValidator;
import aluracursos.forohub_challenge.domain.users.IUserRepository;
import aluracursos.forohub_challenge.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicCrudService {
    @Autowired
    private ITopicRepository topicoRepository;
    @Autowired
    private IUserRepository usuarioRepository;
    @Autowired
    List<ITopicValidator> validadores;

    public TopicDetailsData crear(TopicCreateData datos){
        if(datos.idUsuario()!=null&&!usuarioRepository.existsById(datos.idUsuario())){
            throw new IntegrityValidation("Id de usuario no encontrado");
        }
        validadores.forEach(v->v.validar(datos));
        var usuario = usuarioRepository.findById(datos.idUsuario()).get();
        var topico = new Topic(
                datos.titulo(),
                datos.mensaje(),
                datos.status(),
                usuario,
                datos.nombreCurso()
        );
        topicoRepository.save(topico);
        return new TopicDetailsData(topico);
    }
}
