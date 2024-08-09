package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import com.incubator.edupayroll.util.selection.SelectionType;
import jakarta.transaction.Transactional;
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

  public long count(UserEntity user, Optional<String> query) {
    return teacherRepository.count(user, query.map(String::trim));
  }

  public List<TeacherEntity> getAll(
      UserEntity user, int limit, int offset, Optional<String> query) {
    int number = Math.round((float) offset / limit);
    var searchQuery = query.map(String::trim);

    var sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));

    var pr = PageRequest.of(number, limit, sort);
    var page = teacherRepository.findAllByUser(user, searchQuery, pr);

    return page.get().toList();
  }

  public TeacherEntity getById(UserEntity user, UUID id) {
    var maybeTeacher = teacherRepository.findById(id);
    var teacher = maybeTeacher.orElseThrow(() -> TeacherNotFoundException.byId(id));

    if (!teacher.getUser().getId().equals(user.getId())) {
      throw AccessDeniedException.byUser(user);
    }

    return teacher;
  }

  public TeacherEntity update(
      TeacherEntity teacher, String name, String branch, String idNumber, String description) {
    if (name != null) {
      teacher.setName(name);
    }

    if (branch != null) {
      teacher.setBranch(branch);
    }

    if (idNumber != null) {
      teacher.setIdNumber(idNumber);
    }

    if (description != null) {
      teacher.setDescription(description);
    }

    return teacherRepository.saveAndFlush(teacher);
  }

  public TeacherEntity create(
      String name, String branch, String idNumber, String description, UserEntity user) {
    var teacher = new TeacherEntity(name, branch, idNumber, description, user, new ArrayList<>());
    return teacherRepository.saveAndFlush(teacher);
  }

  public void remove(TeacherEntity teacher) {
    teacherRepository.delete(teacher);
  }

  @Transactional
  public void bulkRemove(UserEntity user, SelectionType type, List<UUID> ids) {
    if (type == SelectionType.INCLUDE) {
      teacherRepository.deleteAll(user, ids);
      return;
    }

    teacherRepository.deleteAllExcludingIds(user, ids);
  }
}
