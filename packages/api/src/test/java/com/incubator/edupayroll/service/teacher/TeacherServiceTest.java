package com.incubator.edupayroll.service.teacher;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TeacherServiceTest {
    @Autowired
    private Faker faker;

    @Autowired
    private TestHelper helper;

    @Autowired
    private TeacherService teacherService;


    @Test
    @Transactional
    @Rollback
    @DisplayName("should create teacher correctly")
    public void testTeacherServiceCreate() {
        var user = helper.createUser();

        var name = faker.name().fullName();
        var branch = faker.job().field();
        var idNumber = faker.number().digits(11);

        var teacher = teacherService.create(name, branch, idNumber, user);

        assertNotNull(teacher);

        assertEquals(teacher.getName(), name);
        assertEquals(teacher.getBranch(), branch);
        assertEquals(teacher.getIdNumber(), idNumber);
        assertEquals(teacher.getUser().getId(), user.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should update teacher correctly")
    public void testTeacherServiceUpdate() {
        var user = helper.createUser();
        var teacher = createTeacher(user);

        var name = teacher.getName();
        var branch = teacher.getBranch();
        var updatedName = faker.name().fullName();

        var updatedTeacher = teacherService.update(teacher, updatedName, null, null);

        assertNotNull(updatedTeacher);

        assertNotEquals(updatedTeacher.getName(), name);
        assertEquals(updatedTeacher.getName(), updatedName);

        assertEquals(updatedTeacher.getBranch(), branch);
        assertEquals(updatedTeacher.getId(), teacher.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should remove teacher correctly")
    public void testTeacherServiceRemove() {
        var user = helper.createUser();
        var teacher = createTeacher(user);

        assertEquals(1, teacherService.count(user));

        teacherService.remove(teacher);

        assertEquals(0, teacherService.count(user));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should get teacher by id correctly")
    public void testTeacherServiceGetById() {
        var user = helper.createUser();
        var teacher = createTeacher(user);

        var foundTeacher = teacherService.getById(user, teacher.getId());

        assertNotNull(foundTeacher);

        assertEquals(foundTeacher.getId(), teacher.getId());
        assertEquals(foundTeacher.getName(), teacher.getName());
        assertEquals(foundTeacher.getUser().getId(), user.getId());
        assertEquals(foundTeacher.getBranch(), teacher.getBranch());
        assertEquals(foundTeacher.getIdNumber(), teacher.getIdNumber());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should get all teachers correctly")
    public void testTeacherServiceGetAll() {
        var user = helper.createUser();
        var anotherUser = helper.createUser();

        var teachers = new ArrayList<TeacherEntity>();

        for (int i = 0; i < 12; i++)
            teachers.add(createTeacher(user));

        var reversed = teachers.reversed();

        for (int i = 0; i < 5; i++)
            createTeacher(anotherUser);


        var teachersPageOne = teacherService.getAll(user, 10, 0);
        var teachersPageTwo = teacherService.getAll(user, 10, 10);

        assertEquals(10, teachersPageOne.size());
        assertEquals(2, teachersPageTwo.size());

        for (int i = 0; i < teachersPageOne.size(); i++)
            assertEquals(teachersPageOne.get(i).getId(), reversed.get(i).getId());

        for (var i = 10; i < teachersPageTwo.size() + 10; i++)
            assertEquals(teachersPageTwo.get(i - 10).getId(), reversed.get(i).getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should count teachers correctly")
    public void testTeacherServiceCount() {
        var user = helper.createUser();

        assertEquals(0, teacherService.count(user));

        for (int i = 0; i < 5; i++)
            createTeacher(user);

        assertEquals(5, teacherService.count(user));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should throw exception when teacher not found")
    public void testTeacherServiceGetByIdNotFound() {
        var user = helper.createUser();
        var teacherId = UUID.fromString(faker.internet().uuid());

        assertThrows(TeacherNotFoundException.class, () -> teacherService.getById(user, teacherId));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("should throw exception when user is not owner of teacher, when getting by id")
    public void testTeacherServiceGetByIdAccessDenied() {
        var user = helper.createUser();
        var anotherUser = helper.createUser();
        var teacher = createTeacher(anotherUser);

        assertThrows(AccessDeniedException.class, () -> teacherService.getById(user, teacher.getId()));
    }

    private TeacherEntity createTeacher(UserEntity user) {
        var name = faker.name().fullName();
        var branch = faker.job().field();
        var idNumber = faker.number().digits(11);

        return teacherService.create(name, branch, idNumber, user);
    }
}
