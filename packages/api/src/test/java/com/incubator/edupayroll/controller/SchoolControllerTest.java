package com.incubator.edupayroll.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.service.user.UserService;
import jakarta.transaction.Transactional;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SchoolControllerTest {
  @Autowired private Faker faker;
  @Autowired private MockMvc mvc;
  @Autowired private TestHelper helper;
  @Autowired private ObjectMapper mapper;
  @MockBean private UserService userService;

  private UserEntity mockedUser;

  @BeforeEach
  public void setup() {
    mockedUser = helper.createUser();
    when(userService.getAuthenticatedUser()).thenReturn(mockedUser);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update school")
  public void testUpdateSchool() throws Exception {
    var school = helper.createSchool(mockedUser);

    var schoolName = school.getName();
    var editorName = school.getEditorName();

    var updatedEditorTitle = faker.name().title();

    mvc.perform(
            put("/school")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("editorTitle", updatedEditorTitle))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value(schoolName))
        .andExpect(jsonPath("$.data.editorName").value(editorName))
        .andExpect(jsonPath("$.data.editorTitle").value(updatedEditorTitle));
  }

  @Test
  @DisplayName("should get school")
  public void testGetSchool() throws Exception {
    var school = helper.createSchool(mockedUser);

    mvc.perform(get("/school").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value(school.getName()))
        .andExpect(jsonPath("$.data.editorName").value(school.getEditorName()))
        .andExpect(jsonPath("$.data.editorTitle").value(school.getEditorTitle()))
        .andExpect(jsonPath("$.data.principalName").value(school.getPrincipalName()));
  }
}
