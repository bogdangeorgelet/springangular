package com.example.springangularapp.springangular;

import com.example.springangularapp.TestContext;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.service.ClientService;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class ClientControlerTest {
    private MockMvc mockMvc;
    @Autowired
    private ClientService clientService;

    @Test
    public void findAllClients_ShouldReturnAllCLients() throws Exception {
        ClientEntity client1 = new ClientEntity();
        client1.setName("client1");
        client1.setCnp("1234");
        client1.setAddress("Oradea");
        ClientEntity client2 = new ClientEntity();
        client2.setName("client2");
        client2.setCnp("4321");
        client2.setAddress("Bihor");
        Mockito.when(clientService.findAllClients()).thenReturn(Arrays.asList(client1, client2));
        mockMvc.perform(get("localhost:8888/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8")
                )))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("client1")));
    }
}
