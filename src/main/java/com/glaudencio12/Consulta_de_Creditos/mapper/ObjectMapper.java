package com.glaudencio12.Consulta_de_Creditos.mapper;


import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origem, Class<D> destino){
        return mapper.map(origem, destino);
    }

    public static <O, D> List<D> parseObjects(List<O> origem, Class<D> destino){
        List<D> objectsList = new ArrayList<D>();
        for (Object o : origem){
            objectsList.add(mapper.map(o, destino));
        }
        return objectsList;
    }
}
