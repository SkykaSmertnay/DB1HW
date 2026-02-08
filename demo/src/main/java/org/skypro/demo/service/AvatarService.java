package org.skypro.demo.service;

import org.skypro.demo.model.Avatar;
import org.skypro.demo.repository.AvatarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Page<Avatar> getAll(Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}
