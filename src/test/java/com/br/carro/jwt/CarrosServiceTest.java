package com.br.carro.jwt;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.carro.dto.CarroDTO;
import com.br.carro.entity.Carro;
import com.br.carro.exception.ObjectNotFoundException;
import com.br.carro.service.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTest {

    @Autowired
    private CarroService service;

    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");
        Optional<CarroDTO> d = service.createCarro(carro);

        CarroDTO c = d.get();

        assertNotNull(c);

        Long id = c.getId();
        assertNotNull(id);

        // Buscar o objeto
        c = service.getCarroById(id);
        assertNotNull(c);

        assertEquals("Porshe",c.getNome());
        assertEquals("esportivos",c.getTipo());

        // Deletar o objeto
        service.deleteCarro(id);

        // Verificar se deletou
        try {
            service.getCarroById(id);
            fail("O carro não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

    @Test
    public void testLista() {

        List<CarroDTO> carros = service.getCarros(PageRequest.of(0, 30));

        assertEquals(30, carros.size());
    }

    @Test
    public void testListaPorTipo() {

        assertEquals(10, service.getCarroByTipo("classicos", PageRequest.of(0, 10)).size());
        assertEquals(10, service.getCarroByTipo("esportivos", PageRequest.of(0, 10)).size());
        assertEquals(10, service.getCarroByTipo("luxo", PageRequest.of(0, 10)).size());

        assertEquals(0, service.getCarroByTipo("x", PageRequest.of(0, 10)).size());
    }

    @Test
    public void testGet() {

        CarroDTO c = service.getCarroById(11L);

        assertNotNull(c);


        assertEquals("Ferrari FF", c.getNome());
    }
}