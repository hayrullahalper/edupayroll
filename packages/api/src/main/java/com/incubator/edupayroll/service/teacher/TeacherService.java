package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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

  public long count(
      UserEntity user, String firstName, String lastName, String branch, String idNumber) {
    return teacherRepository.countFilteredTeachers(user, firstName, lastName, branch, idNumber);
  }

  public List<TeacherEntity> getAll(
      UserEntity user,
      int limit,
      int offset,
      String firstName,
      String lastName,
      String branch,
      String idNumber) {
    int number = Math.round((float) offset / limit);

    var sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));

    var pr = PageRequest.of(number, limit, sort);
    var page =
        teacherRepository.findFilteredTeachers(user, firstName, lastName, branch, idNumber, pr);

    return page.get().toList();
  }

  public TeacherEntity getById(UserEntity user, UUID uuid) {
    var maybeTeacher = teacherRepository.findById(uuid);

    var teacher = maybeTeacher.orElseThrow(() -> TeacherNotFoundException.byUser(user));

    if (!teacher.getUser().getId().equals(user.getId())) throw AccessDeniedException.byUser(user);

    return teacher;
  }

  public TeacherEntity update(
      TeacherEntity teacher, String firstName, String lastName, String branch, String idNumber) {
    if (firstName != null) teacher.setFirstName(firstName);

    if (lastName != null) teacher.setLastName(lastName);

    if (branch != null) teacher.setBranch(branch);

    if (idNumber != null) teacher.setIdNumber(idNumber);

    return teacherRepository.saveAndFlush(teacher);
  }

  public TeacherEntity create(
      String firstName, String lastName, String branch, String idNumber, UserEntity user) {
    var teacher = new TeacherEntity(firstName, lastName, branch, idNumber, user, null);
    return teacherRepository.saveAndFlush(teacher);
  }

  public void remove(TeacherEntity teacher) {
    teacherRepository.delete(teacher);
  }

  public void removeAllIncluding(UserEntity user, List<UUID> ids) {
    List<TeacherEntity> teachersToRemove =
        teacherRepository.findAllById(ids).stream()
            .filter(teacher -> teacher.getUser().getId().equals(user.getId()))
            .collect(Collectors.toList());

    teacherRepository.deleteAll(teachersToRemove);
  }

  public void removeAllExcluding(UserEntity user, List<UUID> ids) {
    List<TeacherEntity> teachersToRemove =
        teacherRepository.findAllByUser(user).stream()
            .filter(teacher -> !ids.contains(teacher.getId()))
            .collect(Collectors.toList());

    teacherRepository.deleteAll(teachersToRemove);
  }
}
