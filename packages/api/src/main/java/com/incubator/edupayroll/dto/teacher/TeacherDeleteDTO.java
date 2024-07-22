package com.incubator.edupayroll.dto.teacher;

import com.incubator.edupayroll.util.selection.DeleteSelectionType;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDeleteDTO {

  private List<UUID> ids;
  private DeleteSelectionType type;

  public TeacherDeleteDTO(List<UUID> ids, DeleteSelectionType type) {
    this.ids = ids;
    this.type = type == null ? DeleteSelectionType.INCLUDE : type;
  }
}
