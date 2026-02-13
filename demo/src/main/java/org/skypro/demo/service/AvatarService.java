package org.skypro.demo.service;

import org.skypro.demo.model.Avatar;
import org.skypro.demo.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Page<Avatar> getAll(Pageable pageable) {
        logger.info("Was invoked method getAll avatars");

        logger.debug("Page request: pageNumber={}, pageSize={}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Avatar> avatars = avatarRepository.findAll(pageable);

        logger.debug("Found {} avatars on current page", avatars.getNumberOfElements());

        return avatars;
    }
}
