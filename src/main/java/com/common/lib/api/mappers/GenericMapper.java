package com.common.lib.api.mappers;

import com.netflix.discovery.provider.Serializer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Serializer
public class GenericMapper<RES,RQ,E> {

    private final ModelMapper modelMapper;

    public GenericMapper() {
        this.modelMapper = new ModelMapper();
    }

    public RES mapToRes(E source, RES resClass) {
        return (RES) modelMapper.map(source, resClass.getClass());
    }

    public  E mapToEntity(RQ source, E entityClass) {

        return (E) modelMapper.map(source, entityClass.getClass());
    }

    public  List<RES> mapListToRes(List<E> sourceList, RES resClass) {
        List<RES> list = new ArrayList<>();
        for (E source : sourceList) {
            RES res = mapToRes(source, resClass);
            list.add(res);
        }
        return list;
    }
}