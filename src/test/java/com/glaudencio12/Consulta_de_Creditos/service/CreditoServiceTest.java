package com.glaudencio12.Consulta_de_Creditos.service;

import com.glaudencio12.Consulta_de_Creditos.MockCredito;
import com.glaudencio12.Consulta_de_Creditos.dto.CreditoDTO;
import com.glaudencio12.Consulta_de_Creditos.exceptions.NotFoundException;
import com.glaudencio12.Consulta_de_Creditos.mapper.ObjectMapper;
import com.glaudencio12.Consulta_de_Creditos.model.Credito;
import com.glaudencio12.Consulta_de_Creditos.repository.CreditoRepository;
import com.glaudencio12.Consulta_de_Creditos.utils.HateoasLinks;
import org.junit.jupiter.api.BeforeEach;
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
    void buscaUmCreditoConstituidoPeloNumero() {
        var creditoDTO = mockCredito.mockCreditoList().getFirst();
        var creditoEntidade = ObjectMapper.parseObject(creditoDTO, Credito.class);

        when(repository.findAll()).thenReturn(List.of(creditoEntidade));
        doCallRealMethod().when(hateoasLinks).links(any(CreditoDTO.class));

        CreditoDTO result = service.findCreditByNumero(creditoDTO.getNumeroCredito());

        assertNotNull(result);
        assertEquals(creditoDTO.getNumeroCredito(), result.getNumeroCredito());

        hateoasLinks(result, "findCreditByNumero", "/api/creditos/credito/123456", "GET");
        hateoasLinks(result, "findAllCredits", "/api/creditos/7891011", "GET");
    }

    @Test
    void buscaTodosOsCreditosContituidosComOMesmoNumeroNfse() {
        var creditoListDTO = mockCredito.mockCreditoList();
        var creditoListEntidade = ObjectMapper.parseListObjects(creditoListDTO, Credito.class);

        when(repository.findAll()).thenReturn(creditoListEntidade);
        doCallRealMethod().when(hateoasLinks).links(any(CreditoDTO.class));

        List<CreditoDTO> result = service.findAllCredit("7891011");

        assertNotNull(result);
        assertEquals(2, result.size());

        CreditoDTO resultOne = result.getFirst();
        hateoasLinks(resultOne, "findCreditByNumero", "/api/creditos/credito/123456", "GET");
        hateoasLinks(resultOne, "findAllCredits", "/api/creditos/7891011", "GET");

        CreditoDTO resultTwo = result.get(1);
        hateoasLinks(resultTwo, "findCreditByNumero", "/api/creditos/credito/789012", "GET");
        hateoasLinks(resultTwo, "findAllCredits", "/api/creditos/7891011", "GET");
    }

    @Test
    void retornaUmaExcecaoCasoNenhumCreditoComONumeroSejaEncontrado(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            service.findCreditByNumero(null);
        });
        String mensagemEsperada = "Nenhum crédito encontrado com o número especificado";
        String mensagemAtual = exception.getMessage();
        assertTrue(mensagemAtual.contains(mensagemEsperada));
    }

    @Test
    void retornaUmaExcecaoCasoNenhumCreditoComONumeroNfseSejaEncontrado(){
        Exception exception = assertThrows(NotFoundException.class, () -> {
            service.findAllCredit(null);
        });
        String mensagemEsperada = "Nenhum crédito encontrado com o número NFS-E especificado";
        String mensagemAtual = exception.getMessage();
        assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}