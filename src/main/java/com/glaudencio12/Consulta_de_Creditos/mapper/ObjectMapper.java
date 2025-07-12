package com.glaudencio12.Consulta_de_Creditos.mapper;
import org.modelmapper.ModelMapper;

public class ObjectMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origem, Class<D> destino){
        return mapper.map(origem, destino);
    }
}
