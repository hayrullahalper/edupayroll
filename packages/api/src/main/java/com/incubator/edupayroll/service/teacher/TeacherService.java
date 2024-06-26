package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public long count(UserEntity user) {
        return teacherRepository.countByUser(user);
    }

    public List<TeacherEntity> getAll(UserEntity user, int limit, int offset) {
        int number = Math.round((float) offset / limit);

        var pr = PageRequest.of(number, limit);
        var page = teacherRepository.findAllByUser(user, pr);

        return page.get().toList();
    }

    public TeacherEntity getById(UserEntity user, UUID uuid) {
        var maybeTeacher = teacherRepository.findById(uuid);

        var teacher = maybeTeacher.orElseThrow(() -> TeacherNotFoundException.byUser(user));

        if (!teacher.getUser().getId().equals(user.getId()))
            throw AccessDeniedException.byUser(user);

        return teacher;
    }

    public TeacherEntity update(TeacherEntity teacher, String name, String branch, String idNumber) {
        if (name != null)
            teacher.setName(name);

        if (branch != null)
            teacher.setBranch(branch);

        if (idNumber != null)
            teacher.setIdNumber(idNumber);

        return teacherRepository.saveAndFlush(teacher);
    }

    public TeacherEntity create(String name, String branch, String idNumber, UserEntity user) {
        var teacher = new TeacherEntity(name, branch, idNumber, user);
        return teacherRepository.saveAndFlush(teacher);
    }

}
