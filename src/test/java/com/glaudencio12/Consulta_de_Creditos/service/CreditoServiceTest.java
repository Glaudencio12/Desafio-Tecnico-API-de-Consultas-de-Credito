package com.glaudencio12.Consulta_de_Creditos.service;

import com.glaudencio12.Consulta_de_Creditos.mock.MockCredito;
import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import com.glaudencio12.Consulta_de_Creditos.exceptions.NotFoundException;
import com.glaudencio12.Consulta_de_Creditos.mapper.ObjectMapper;
import com.glaudencio12.Consulta_de_Creditos.model.Credito;
import com.glaudencio12.Consulta_de_Creditos.repository.CreditoRepository;
import com.glaudencio12.Consulta_de_Creditos.utils.HateoasLinks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    HateoasLinks hateoasLinks;

    @Mock
    CreditoRepository repository;

    @InjectMocks
    CreditoService service;

    MockCredito mockCredito;

    @BeforeEach
    void setUp() {
        mockCredito = new MockCredito();
    }

    public static void hateoasLinks(CreditoDTO dto, String rel, String href, String tipo){
        assertTrue(dto.getLinks().stream().anyMatch(link ->
                link.getRel().value().equals(rel) &&
                link.getHref().endsWith(href) &&
                link.getType().equals(tipo)
        ));
    }

    @Test
    @DisplayName("Busca um crédito constituído pelo seu número")
    void findCreditByNumber() {
        var creditoDTO = mockCredito.mockCreditoList().getFirst();
        var creditoEntidade = ObjectMapper.parseObject(creditoDTO, Credito.class);

        when(repository.findAll()).thenReturn(List.of(creditoEntidade));
        doCallRealMethod().when(hateoasLinks).links(any(CreditoDTO.class));

        CreditoDTO result = service.findCreditByNumber(creditoDTO.getNumeroCredito());

        assertNotNull(result);
        assertEquals(creditoDTO.getNumeroCredito(), result.getNumeroCredito());

        hateoasLinks(result, "findCreditByNumber", "/api/creditos/credito/123456", "GET");
        hateoasLinks(result, "findAllCredits", "/api/creditos/7891011", "GET");
    }

    @Test
    @DisplayName("Busca um conjunto de créditos contituídos pelo número NSF-E")
    void findAllCreditNumberNsfe() {
        var creditoListDTO = mockCredito.mockCreditoList();
        var creditoListEntidade = ObjectMapper.parseListObjects(creditoListDTO, Credito.class);

        when(repository.findAll()).thenReturn(creditoListEntidade);
        doCallRealMethod().when(hateoasLinks).links(any(CreditoDTO.class));

        List<CreditoDTO> result = service.findAllCreditNumberNsfe("7891011");

        assertNotNull(result);
        assertEquals(2, result.size());

        CreditoDTO resultOne = result.getFirst();
        hateoasLinks(resultOne, "findCreditByNumber", "/api/creditos/credito/123456", "GET");
        hateoasLinks(resultOne, "findAllCredits", "/api/creditos/7891011", "GET");

        CreditoDTO resultTwo = result.get(1);
        hateoasLinks(resultTwo, "findCreditByNumber", "/api/creditos/credito/789012", "GET");
        hateoasLinks(resultTwo, "findAllCredits", "/api/creditos/7891011", "GET");
    }

    @Test
    @DisplayName("Retorna uma exceção caso o crédito com o número especificado não seja encontrado")
    void exceptionNotFoundCredit(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            service.findCreditByNumber(null);
        });
        String mensagemEsperada = "Nenhum crédito encontrado com o número especificado";
        String mensagemAtual = exception.getMessage();
        assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    @DisplayName("Retorna uma exceção caso os créditos com o número NFS-E especificado não sejam encontrados")
    void exceptionNotFoundCredits(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            service.findAllCreditNumberNsfe(null);
        });
        String mensagemEsperada = "Nenhum crédito encontrado com o número NFS-E especificado";
        String mensagemAtual = exception.getMessage();
        assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}