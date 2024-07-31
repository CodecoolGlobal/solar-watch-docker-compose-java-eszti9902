package com.codecool.solarwatch;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.model.payload.CreateUserRequest;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.codecool.solarwatch")
class SolarWatchIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SunriseSunsetRepository sunriseSunsetRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser(roles = "USER")
    void testGetSolarWatchDateFromDb() throws Exception {
        cityRepository.deleteAll();
        sunriseSunsetRepository.deleteAll();

        City city = new City();
        city.setCityName("London");
        city.setCountry("GB");
        city.setState("England");
        city.setLatitude(51.5074F);
        city.setLongitude((float) -0.1278);
        cityRepository.save(city);

        SunriseSunset sunriseSunset = new SunriseSunset();
        sunriseSunset.setCityName("London");
        sunriseSunset.setCity(city);
        sunriseSunset.setDate("2024-07-29");
        sunriseSunset.setSunrise("05:30:00 AM");
        sunriseSunset.setSunset("20:30:00 PM");
        sunriseSunsetRepository.save(sunriseSunset);

        mockMvc.perform(get("/solarwatch")
                        .param("city", "London")
                        .param("date", "2024-07-29")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("London"));
    }

    @Test
    void testUserCreation() throws Exception {
        userRepository.deleteAll();
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Pista");
        request.setPassword("pista");
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUserPromotionToAdmin() throws Exception {
        String username = "NonExistingUser";
        mockMvc.perform(put("/user/promote/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}
