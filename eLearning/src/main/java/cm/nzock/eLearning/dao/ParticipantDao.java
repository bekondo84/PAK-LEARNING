package cm.nzock.eLearning.dao;

import cm.platform.myleaninig.core.ParticipantModel;

import java.util.Optional;

public interface ParticipantDao {
    Optional<ParticipantModel> findByUsername(final String username);
}
