package com.example.springangularapp.springangular;

import com.example.springangularapp.SpringAngularApplication;
import com.example.springangularapp.dto.CompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringAngularApplication.class})
@WebAppConfiguration
public class UserControlerTest {

    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void register_shouldRegisterAnewCompany() throws Exception {

        mockMvc.perform(post("/register")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8")))
                .content(jsonWrapper()))
                .andExpect(status().isCreated());
    }



    @Test
    public void login_shouldLoginACompany() throws Exception{
        mockMvc.perform(post("/login")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"))))
                .andExpect(status().isOk());
    }

    private String jsonWrapper() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("petrisor");
        companyDto.setEmail("nu@merge.com");
        companyDto.setPassword("parola");

        ObjectMapper mapperObj = new ObjectMapper();

        try {
            String jsonStr = mapperObj.writeValueAsString(companyDto);
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
