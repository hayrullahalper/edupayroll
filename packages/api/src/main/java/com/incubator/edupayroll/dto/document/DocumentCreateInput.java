package com.incubator.edupayroll.dto.document;

import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DocumentCreateInput {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private String description;

    @NotNull
    private List<ExportEntity> exports;

    @NotNull
    private List<RecordEntity> records;

}
