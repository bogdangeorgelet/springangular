package com.example.springangularapp.springangular;

import com.example.springangularapp.SpringAngularApplication;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringAngularApplication.class})
@WebAppConfiguration
public class ClientControlerTest {

    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findAllClients_ShouldReturnAllCLients() throws Exception {
        ClientEntity client1 = new ClientEntity();
        client1.setName("client1");
        client1.setCnp("1234");
        client1.setAddress("Oradea");
        clientRepository.save(client1);

        ClientEntity client2 = new ClientEntity();
        client2.setName("client2");
        client2.setCnp("4321");
        client2.setAddress("Bihor");
        clientRepository.save(client2);
//        Mockito.when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("client1")))
                .andExpect(jsonPath("$.content[1].name", is("client2")))
                .andExpect(jsonPath("$.content[0].cnp", is("1234")))
                .andExpect(jsonPath("$.content[0].address", is("Oradea")))
                .andExpect(jsonPath("$.content[1].cnp", is("4321")))
                .andExpect(jsonPath("$.content[1].address", is("Bihor")));
    }

    @Test
    public void getClientByid() throws Exception {
        mockMvc.perform(get("/client/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(jsonPath("$.name", is("petrica")));
    }
}
