package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.entity.record.RecordType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordCreateDTO {

    RecordType type;
    String information;
    UUID teacher;

}
