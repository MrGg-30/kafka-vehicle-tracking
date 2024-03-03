package com.epam.msvehicletracking.mapper;

import com.epam.msvehicletracking.dto.VehicleSignalRequest;
import com.epam.msvehicletracking.model.Signal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ApiSignalRequestMapper {

    @Mapping(source = "latitude", target = "coordinate.latitude")
    @Mapping(source = "longitude", target = "coordinate.longitude")
    public abstract Signal map(VehicleSignalRequest request);
}
