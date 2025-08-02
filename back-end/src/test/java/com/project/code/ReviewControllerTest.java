package com.project.code;

import com.project.code.Controller.ReviewController;
import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewRepository reviewRepository;

    @MockitoBean
    private CustomerRepository customerRepository;

    @Test
    void testGetReviews_withCustomerNames() throws Exception {
        Review review = new Review();
        Customer customer = new Customer();

        when(reviewRepository.findByStoreIdAndProductId(1L, 1L)).thenReturn(List.of(review));
        when(customerRepository.findByid(1L)).thenReturn(customer);

        mockMvc.perform(get("/reviews/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviews[0].review").value("Excellent product"))
                .andExpect(jsonPath("$.reviews[0].rating").value(5))
                .andExpect(jsonPath("$.reviews[0].customerName").value("Alice"));
    }

    @Test
    void testGetReviews_customerNotFound() throws Exception {
        Review review = new Review();

        when(reviewRepository.findByStoreIdAndProductId(2L, 2L)).thenReturn(List.of(review));
        when(customerRepository.findByid(2L)).thenReturn(null);

        mockMvc.perform(get("/reviews/2/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviews[0].review").value("Decent quality"))
                .andExpect(jsonPath("$.reviews[0].rating").value(4))
                .andExpect(jsonPath("$.reviews[0].customerName").value("Unknown"));
    }

    @Test
    void testGetAllReviews() throws Exception {
        Review review = new Review();

        when(reviewRepository.findAll()).thenReturn(List.of(review));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviews[0].comment").value("Not bad"))
                .andExpect(jsonPath("$.reviews[0].rating").value(3));
    }
}
