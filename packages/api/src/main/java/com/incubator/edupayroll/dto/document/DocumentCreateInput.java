package com.incubator.edupayroll.dto.document;

import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class DocumentCreateInput {

    @NotNull
    public String name;

    @NotNull
    public LocalDateTime time;

    @NotNull
    public String description;

    @NotNull
    public List<ExportEntity> exports;

    @NotNull
    public List<RecordEntity> records;

}
