package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.dto.teacher.TeacherCreationDTO;
import com.incubator.edupayroll.dto.teacher.TeacherDTO;
import com.incubator.edupayroll.entity.TeacherEntity;
import com.incubator.edupayroll.entity.UserEntity;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(UserRepository userRepository, TeacherRepository teacherRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherDTO> getAll(int limit, int offset) {
        int number = Math.round((float) offset / limit);

        PageRequest pr = PageRequest.of(number, limit);
        Page<TeacherEntity> page = teacherRepository.findAll(pr);

        return page.get().map(this::mapToTeacherDTO).toList();
    }

    public TeacherDTO create(TeacherCreationDTO teacherCreationDTO) {
        var maybeUser = userRepository.findById(UUID.fromString("573a6bbf-31f7-4cd3-a778-6e696d1525bc"));
        var user = maybeUser.orElseThrow();

        var teacher = mapToTeacherEntity(user, teacherCreationDTO);

        return mapToTeacherDTO(teacherRepository.saveAndFlush(teacher));
    }

    private TeacherDTO mapToTeacherDTO(TeacherEntity teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getBranch(),
                teacher.getIdNumber()
        );
    }

    private TeacherEntity mapToTeacherEntity(UserEntity userEntity, TeacherCreationDTO teacherCreationDTO) {
        return new TeacherEntity(
                teacherCreationDTO.getName(),
                teacherCreationDTO.getBranch(),
                teacherCreationDTO.getIdNumber(),
                userEntity
        );
    }

}
