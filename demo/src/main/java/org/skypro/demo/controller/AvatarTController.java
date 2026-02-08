package org.skypro.demo.controller;

import org.skypro.demo.model.Avatar;
import org.skypro.demo.service.AvatarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatars")
public class AvatarTController {

    private final AvatarService avatarService;

    public AvatarTController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public Page<Avatar> getAllAvatars(@RequestParam int page, @RequestParam int size) {
        return avatarService.getAll(PageRequest.of(page, size));
    }
}

