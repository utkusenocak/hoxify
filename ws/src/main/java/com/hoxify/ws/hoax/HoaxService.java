package com.hoxify.ws.hoax;

import com.hoxify.ws.file.FileAttachment;
import com.hoxify.ws.file.FileAttachmentRepository;
import com.hoxify.ws.file.FileService;
import com.hoxify.ws.hoax.vm.HoaxSubmitVM;
import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;
    UserService userService;
    FileAttachmentRepository fileAttachmentRepository;

    FileService fileService;

    public HoaxService(HoaxRepository hoaxRepository, FileAttachmentRepository fileAttachmentRepository,
                       FileService fileService) {
        this.hoaxRepository = hoaxRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void save(HoaxSubmitVM hoaxSubmitVM, User user) {
        Hoax hoax = new Hoax();
        hoax.setContent(hoaxSubmitVM.getContent());
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
        if (hoaxSubmitVM.getAttachmentId() != null) {
            Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository
                    .findById(hoaxSubmitVM.getAttachmentId());
            if (optionalFileAttachment.isPresent()) {
                FileAttachment fileAttachment = optionalFileAttachment.get();
                fileAttachment.setHoax(hoax);
                fileAttachmentRepository.save(fileAttachment);
            }
        }

    }

    public Page<Hoax> getHoaxes(Pageable pageable) {
        return hoaxRepository.findAll(pageable);
    }

    public Page<Hoax> getUserHoaxes(Pageable pageable, String username) {
        User user = userService.getByUsername(username);
        return hoaxRepository.findByUser(user, pageable);
    }

    public Page<Hoax> getOldHoaxes(Long id, String username, Pageable pageable) {
        Specification<Hoax> specification = idLessThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification, pageable);
    }

    public Long getNewHoaxesCount(Long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.count(specification);
    }

    public List<Hoax> getNewHoaxes(Long id, String username, Sort sort) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification, sort);
    }

    public void delete(Long id) {
        Hoax hoax = hoaxRepository.getById(id);
        if (hoax.getFileAttachment() != null) {
            String filename = hoax.getFileAttachment().getName();
            fileService.deleteAttachmentFile(filename);
        }
        hoaxRepository.deleteById(id);
    }

    Specification<Hoax> idLessThan(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    Specification<Hoax> userIs(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    Specification<Hoax> idGreaterThan(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), id);
    }

    public void deleteHoaxesOfUser(String username) {
        User user = userService.getByUsername(username);
        Specification<Hoax> userOwned = userIs(user);
        List<Hoax> hoaxesToBeRemoved = hoaxRepository.findAll(userOwned);
        hoaxRepository.deleteAll(hoaxesToBeRemoved);
    }
}
