package com.common.lib.api.mappers;

import com.common.lib.api.dtos.request.AuditRequest;
import com.common.lib.api.response.AuditResponse;
import com.common.lib.infraestructure.entitis.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel ="spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuditMapper {

    AuditResponse map(Audit source);

    Audit map( AuditRequest source);

    List<AuditResponse> mapList(List<Audit> sources);


}
