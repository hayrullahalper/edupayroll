package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.Teacher;
import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public long count(User user) {
        return teacherRepository.countByUser(user);
    }

    public List<Teacher> getAll(User user, int limit, int offset) {
        int number = Math.round((float) offset / limit);

        var pr = PageRequest.of(number, limit);
        var page = teacherRepository.findAllByUser(user, pr);

        return page.get().toList();
    }
}
