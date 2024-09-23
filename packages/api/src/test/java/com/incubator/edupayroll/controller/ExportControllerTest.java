package com.incubator.edupayroll.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.common.selection.SelectionType;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.ExportRepository;
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
public class ExportControllerTest {
  @Autowired private Faker faker;
  @Autowired private MockMvc mvc;
  @Autowired private TestHelper helper;
  @Autowired private ObjectMapper mapper;
  @MockBean private UserService userService;
  @Autowired private ExportRepository exportRepository;

  private UserEntity mockedUser;
  private DocumentEntity mockedDocument;

  @BeforeEach
  public void setup() {
    mockedUser = helper.createUser();
    mockedDocument = helper.createDocument(mockedUser);

    when(userService.getAuthenticatedUser()).thenReturn(mockedUser);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all exports")
  public void testGetExports() throws Exception {
    var last = helper.createExport(mockedDocument);

    helper.createExport(mockedDocument);
    helper.createExport(mockedDocument);

    mvc.perform(get("/exports?limit=2&offset=0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.length()").value(2))
        .andExpect(jsonPath("$.meta.offset").value(0))
        .andExpect(jsonPath("$.meta.limit").value(2))
        .andExpect(jsonPath("$.meta.total").value(3));

    mvc.perform(get("/exports?limit=2&offset=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.length()").value(1))
        .andExpect(jsonPath("$.meta.offset").value(2))
        .andExpect(jsonPath("$.meta.limit").value(2))
        .andExpect(jsonPath("$.meta.total").value(3))
        .andExpect(jsonPath("$.data[0].id").value(last.getId().toString()))
        .andExpect(jsonPath("$.data[0].name").value(last.getName()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update export name")
  public void testUpdateExportName() throws Exception {
    var export = helper.createExport(mockedDocument);

    var name = faker.name().title();

    mvc.perform(
            put("/exports/" + export.getId() + "/name")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", name))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.id").value(export.getId().toString()))
        .andExpect(jsonPath("$.data.name").value(name));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should delete export")
  public void testDeleteExport() throws Exception {
    var export = helper.createExport(mockedDocument);

    mvc.perform(delete("/exports/" + export.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.success").value(true));

    assertFalse(exportRepository.existsById(export.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should delete exports in bulk")
  public void testDeleteExports() throws Exception {
    var export1 = helper.createExport(mockedDocument);
    var export2 = helper.createExport(mockedDocument);

    mvc.perform(
            delete("/exports/bulk")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "ids",
                            new String[] {
                              export1.getId().toString(), export2.getId().toString()
                            }))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.success").value(true));

    assertFalse(exportRepository.existsById(export1.getId()));
    assertFalse(exportRepository.existsById(export2.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should bulk delete exports")
  public void testBulkDeleteExports() throws Exception {
    var export1 = helper.createExport(mockedDocument);
    var export2 = helper.createExport(mockedDocument);
    var export3 = helper.createExport(mockedDocument);
    var export4 = helper.createExport(mockedDocument);

    mvc.perform(
            delete("/exports/bulk")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "ids",
                            new String[] {
                              export1.getId().toString(), export2.getId().toString()
                            }))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data.success").value(true));

    assertTrue(exportRepository.existsById(export3.getId()));
    assertTrue(exportRepository.existsById(export4.getId()));

    assertFalse(exportRepository.existsById(export1.getId()));
    assertFalse(exportRepository.existsById(export2.getId()));

    mvc.perform(
            delete("/exports/bulk")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "ids",
                            new String[] {export3.getId().toString()},
                            "type",
                            SelectionType.EXCLUDE))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data.success").value(true));

    assertTrue(exportRepository.existsById(export3.getId()));
    assertFalse(exportRepository.existsById(export4.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should return not found error when export not found")
  public void testExportNotFound() throws Exception {
    mvc.perform(delete("/exports/" + faker.internet().uuid()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors.length()").value(1))
        .andExpect(jsonPath("$.errors[0].code").value("EXPORT_NOT_FOUND"));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should return access denied error when user is not owner of export")
  public void testTeacherAccessDenied() throws Exception {
    var export = helper.createExport(mockedDocument);
    var anotherUser = helper.createUser();

    when(userService.getAuthenticatedUser()).thenReturn(anotherUser);

    mvc.perform(
            put("/exports/" + export.getId() + "/name")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", "updated name"))))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("errors[0].code").value("ACCESS_DENIED"));
  }
}
