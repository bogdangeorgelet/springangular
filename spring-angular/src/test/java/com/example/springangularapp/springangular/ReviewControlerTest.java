package com.example.springangularapp.springangular;

import com.example.springangularapp.SpringAngularApplication;
import com.example.springangularapp.controller.UserController;
import com.example.springangularapp.dto.ClientDto;
import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.dto.ReviewDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.entity.ReviewEntity;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
import com.example.springangularapp.repository.ReviewRepository;
import com.example.springangularapp.service.CompanyService;
import com.example.springangularapp.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
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
public class ReviewControlerTest {

    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebApplicationContext context;

    MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setup() {


        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getReviewByCompany_ShouldReturnAllReviewsWroteByACompany() throws Exception {
        CompanyDto companyEntity = new CompanyDto();
        companyEntity.setPassword("pass");
        companyEntity.setName("petrica");
        companyEntity.setEmail("nu@are.com");
        ResponseEntity<?> x = userController.createUser(companyEntity);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAddress("oradea");
        clientEntity.setCnp("cnp");
        clientEntity.setName("nume");
        ClientEntity clientEntity1 = clientRepository.save(clientEntity);

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setValue(5);
        reviewEntity.setText("ok");
        reviewEntity.setClient(clientEntity1);
        CompanyEntity c = companyRepository.findByEmail("nu@are.com");
        System.out.println("id companie gasit dupa email:" + c.getId());
        reviewEntity.setCompanyEntity(c);
        reviewRepository.save(reviewEntity);

        System.out.println("id companie:" + 1);
        mockMvc.
                perform(get("/reviews/company/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is(5.0)))
                .andExpect(jsonPath("$[0].text", is("ok")))
                .andExpect(jsonPath("$[0].companyName", is("petrica")))
                .andExpect(jsonPath("$[0].clientName", is("nume")));
    }

    @Test
    public void getReviewsByClient_ShouldReturnAllReviewsByAClient() throws Exception {
        mockMvc.
                perform(get("/reviews/client/" + insertClientAndCompanyAndReturnClientId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is(5.0)))
                .andExpect(jsonPath("$[0].text", is("ok")))
                .andExpect(jsonPath("$[0].companyName", is("petrica")))
                .andExpect(jsonPath("$[0].clientName", is("nume")));
    }

    @Test
    public void getAllReviews_shouldReturnAllReviews() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setPassword("pass");
        companyDto.setName("petrica");
        companyDto.setEmail("nu@are.com");
        userController.createUser(companyDto);

        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setPassword(companyDto.getPassword());
        companyEntity1.setEmail(companyDto.getEmail());
        companyEntity1.setName(companyDto.getName());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAddress("oradea");
        clientEntity.setCnp("cnp");
        clientEntity.setName("nume");
        ClientEntity clientEntity1 = clientRepository.save(clientEntity);

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setValue(5);
        reviewEntity.setText("ok");
        reviewEntity.setClient(clientEntity1);
        CompanyEntity c = companyRepository.findByEmail("nu@are.com");

        System.out.println("id companie gasit dupa email:" + c.getId());
        reviewEntity.setCompanyEntity(c);
        reviewRepository.save(reviewEntity);
        ReviewEntity reviewEntity1 = new ReviewEntity();
        reviewEntity1.setValue(1);
        reviewEntity1.setText("sparge tot");
        reviewEntity1.setCompanyEntity(companyRepository.findByEmail("nu@are.com"));
        reviewEntity1.setClient(clientEntity1);
        reviewRepository.save(reviewEntity1);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].value", is(5.0)))
                .andExpect(jsonPath("$[0].text", is("ok")))
                .andExpect(jsonPath("$[1].text", is("sparge tot")))
                .andExpect(jsonPath("$[1].value", is(1.0)));

    }

    @Test
    public void insertAReview_shoulInsertAReviewInDb() throws Exception {
        CompanyDto companyEntity = new CompanyDto();
        companyEntity.setPassword("pass");
        companyEntity.setName("petrica");
        companyEntity.setEmail("nu@are.com");
        userController.createUser(companyEntity);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAddress("oradea");
        clientEntity.setCnp("cnp");
        clientEntity.setName("nume");
        clientRepository.save(clientEntity);


        mockMvc.perform(post("/review/company/1client/1")
                .contentType(mediaType)
                .content(jsonWrapper()))
                .andExpect(status().isOk());
    }


    public int insertClientAndCompanyAndReturnClientId() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setPassword("pass");
        companyDto.setName("petrica");
        companyDto.setEmail("nu@are.com");
        userController.createUser(companyDto);

        CompanyEntity companyEntity1 = new CompanyEntity();
        companyEntity1.setPassword(companyDto.getPassword());
        companyEntity1.setEmail(companyDto.getEmail());
        companyEntity1.setName(companyDto.getName());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAddress("oradea");
        clientEntity.setCnp("cnp");
        clientEntity.setName("nume");
        ClientEntity clientEntity1 = clientRepository.save(clientEntity);

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setValue(5);
        reviewEntity.setText("ok");
        reviewEntity.setClient(clientEntity1);
        CompanyEntity c = companyRepository.findByEmail("nu@are.com");

        System.out.println("id companie gasit dupa email:" + c.getId());
        reviewEntity.setCompanyEntity(c);
        reviewRepository.save(reviewEntity);
        return clientEntity1.getId();
    }

    public String jsonWrapper() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setValue(5);
        reviewDto.setText("ok");

        ObjectMapper mapperObj = new ObjectMapper();

        try {
            String jsonStr = mapperObj.writeValueAsString(reviewDto);
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
