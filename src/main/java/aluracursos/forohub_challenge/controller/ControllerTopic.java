package aluracursos.forohub_challenge.controller;

import aluracursos.forohub_challenge.domain.topics.*;
import aluracursos.forohub_challenge.domain.users.IUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class ControllerTopic {
    @Autowired
    private TopicCrudService crudTopicoService;
    @Autowired
    private IUserRepository usuarioRepository;
    @Autowired
    private ITopicRepository topicoRepository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Crea un tópico",
            description = "Requiere autor, título...",
            tags = {"post"}
    )
    public ResponseEntity crear(@RequestBody @Valid TopicCreateData datos,
                                UriComponentsBuilder uriComponentsBuilder){
        var response = crudTopicoService.crear(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(datos);

    }

    @GetMapping
    @Operation(
            summary = "Retorna todos los tópicos",
            description = "Trae todos los tópicos de la base de datos ordenados por fecha.",
            tags = {"get"}
    )
    public ResponseEntity<Page<TopicListData>> listar(@PageableDefault(size=10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(TopicListData::new));
    }

    @GetMapping("/filtrarxcurso")
    @Operation(
            summary = "Retorna todos los tópicos ordenados por curso",
            description = "Trae todos los tópicos de la base de datos ordenados por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<TopicListData>> listarPorNombreDeCurso(@PageableDefault(size=10, sort = "nombreCurso", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(TopicListData::new));
    }

    @GetMapping("/filtrarxcursoxaño")
    @Operation(
            summary = "Devuelve todos los tópicos ordenados por año",
            description = "Trae todos los tópicos de la base de datos ordenados por curso.",
            tags = {"get"}
    )
    public ResponseEntity<Page<TopicListData>> listarPorAnio(
            @RequestParam int year,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Topic> topicos = topicoRepository.findByFechaYear(year, paginacion);
        Page<TopicListData> datosListadoTopicos = topicos.map(TopicListData::new);
        return ResponseEntity.ok(datosListadoTopicos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un tópico por id",
            description = "Trae el tópico indicado.",
            tags = {"getById"}
    )
    public ResponseEntity<TopicResponseData> retornaDatosTopico(@PathVariable Long id){
        Topic topico = topicoRepository.getReferenceById(id);
        if(topico!=null){
            throw new ValidationException("Id de tópico no existe en la base de datos.");
        }
        var datosTopico = new TopicResponseData(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutores().getUsername(),
                topico.getNombreCurso(),
                topico.getFecha());
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza un tópico",
            description = "Permite actualizar título, mensaje y/o nombre del curso",
            tags = {"put"}
    )
    public ResponseEntity actualizarTopico(@RequestBody @Valid TopicUpdateData datosActualizarTopico){
        Boolean topico = Optional.of(topicoRepository.getReferenceById(datosActualizarTopico.id())).isPresent();
        if(!topico){
            throw new ValidationException("El id del tópico no existe en la base de datos");
        }
        Topic topic = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topic.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(
                new TopicResponseData(
                        topic.getId(),
                        topic.getTitulo(),
                        topic.getMensaje(),
                        topic.getStatus(),
                        topic.getAutores().getUsername(),
                        topic.getNombreCurso(),
                        topic.getFecha()
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    @Operation(
            summary = "Cancela un tópico",
            tags = {"delete"}
    )
    public ResponseEntity eliminar(@PathVariable Long id){
        Topic topico = topicoRepository.getReferenceById(id);
        if(topico!=null){
            throw new ValidationException("Id de tópico no existe en la base de datos.");
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
