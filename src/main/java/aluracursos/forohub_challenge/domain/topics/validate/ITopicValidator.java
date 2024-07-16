package aluracursos.forohub_challenge.domain.topics.validate;

import aluracursos.forohub_challenge.domain.topics.TopicCreateData;

public interface ITopicValidator {
    public void validar(TopicCreateData datos);
}