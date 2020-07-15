package com.br.carro;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.carro.model.UploadInput;
import com.br.carro.model.UploadOutput;
import com.br.carro.service.FirebaseStorageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarroApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadTest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FirebaseStorageService service;

    private TestRestTemplate basicAuth() {
        return rest.withBasicAuth("admin","123");
    }

    private UploadInput getUploadInput() {
        UploadInput upload = new UploadInput();
        upload.setFileName("nome.txt");
        // Base64 de Janderson Cassan
        upload.setBase64("SmFuZGVyc29uIENhc3Nhbg==");
        upload.setMimeType("text/plain");
        return upload;
    }

    @Test
    public void testUploadFirebase() {
        String url = service.upload(getUploadInput());

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    @Test
    public void testUploadAPI() {

        UploadInput upload = getUploadInput();

        // Insert
        ResponseEntity<UploadOutput> response = basicAuth().postForEntity("/api/v1/carros/upload", upload, UploadOutput.class);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UploadOutput out = response.getBody();
        assertNotNull(out);
        System.out.println(out);

        String url = out.getUrl();

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

}