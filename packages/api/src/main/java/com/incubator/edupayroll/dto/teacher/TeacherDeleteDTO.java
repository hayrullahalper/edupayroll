package com.incubator.edupayroll.dto.teacher;

import com.incubator.edupayroll.util.selection.SelectionType;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDeleteDTO {

  private List<UUID> ids;
  private SelectionType type;

  public TeacherDeleteDTO(List<UUID> ids, SelectionType type) {
    this.ids = ids;
    this.type = type == null ? SelectionType.INCLUDE : type;
  }
}
