package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import com.incubator.edupayroll.util.selection.SelectionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;

  @Autowired
  public TeacherService(TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  public long count(UserEntity user, Optional<String> name) {
    return teacherRepository.count(user, name);
  }

  public List<TeacherEntity> getAll(UserEntity user, int limit, int offset, Optional<String> name) {
    int number = Math.round((float) offset / limit);
    var teacherName = name.map(String::trim);

    var sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));

    var pr = PageRequest.of(number, limit, sort);
    var page = teacherRepository.findAllByUser(user, teacherName, pr);

    return page.get().toList();
  }

  public TeacherEntity getById(UserEntity user, UUID uuid) {
    var maybeTeacher = teacherRepository.findById(uuid);

    var teacher = maybeTeacher.orElseThrow(() -> TeacherNotFoundException.byUser(user));

    if (!teacher.getUser().getId().equals(user.getId())) throw AccessDeniedException.byUser(user);

    return teacher;
  }

  public TeacherEntity update(TeacherEntity teacher, String name, String branch, String idNumber) {
    if (name != null) {
      teacher.setName(name);
    }

    if (branch != null) {
      teacher.setBranch(branch);
    }

    if (idNumber != null) {
      teacher.setIdNumber(idNumber);
    }

    return teacherRepository.saveAndFlush(teacher);
  }

  public TeacherEntity create(String name, String branch, String idNumber, UserEntity user) {
    var teacher = new TeacherEntity(name, branch, idNumber, user, new ArrayList<>());
    return teacherRepository.saveAndFlush(teacher);
  }

  public void remove(TeacherEntity teacher) {
    teacherRepository.delete(teacher);
  }

  public void bulkRemove(UserEntity user, SelectionType type, List<UUID> ids) {
    if (type == SelectionType.INCLUDE) {
      teacherRepository.deleteAll(user, ids);
      return;
    }

    teacherRepository.deleteAllExcludingIds(user, ids);
  }
}
