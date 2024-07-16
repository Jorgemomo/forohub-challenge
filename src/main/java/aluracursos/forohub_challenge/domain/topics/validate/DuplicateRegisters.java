package aluracursos.forohub_challenge.domain.topics.validate;

import aluracursos.forohub_challenge.domain.topics.TopicCreateData;
import aluracursos.forohub_challenge.domain.topics.ITopicRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DuplicateRegisters implements ITopicValidator {
    @Autowired
    private ITopicRepository topicoRepository;

    public void validar(TopicCreateData datos){
        var titulo = datos.titulo();
        var mensaje = datos.mensaje();

        var registroDuplicado = topicoRepository.existsByTituloAndMensaje(titulo, mensaje);

        if(registroDuplicado){
            throw new ValidationException("Registro duplicado...");
        }
    }

}

