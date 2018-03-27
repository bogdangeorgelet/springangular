package com.example.springangularapp.springangular;

import com.example.springangularapp.SpringAngularApplication;
import com.example.springangularapp.controller.UserController;
import com.example.springangularapp.dto.CompanyDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringAngularApplication.class})
@WebAppConfiguration
public class CompanyControlerTest {

    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserController userController;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllCompanies_ShouldReturnAllCompanies() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setPassword("pass");
        companyDto.setName("petrica");
        companyDto.setEmail("nu@are.com");
        userController.createUser(companyDto);
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("petrica")))
                .andExpect(jsonPath("$[0].email", is("nu@are.com")));

    }

    @Test
    public void getCompanyById_shouldReturnCompanyById() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setPassword("pass");
        companyDto.setName("petrica");
        companyDto.setEmail("nu@are.com");
        userController.createUser(companyDto);
        mockMvc.perform(get("/company/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(jsonPath("$.name", is("petrica")))
                .andExpect(jsonPath("$.email", is("nu@are.com")));

    }
}
