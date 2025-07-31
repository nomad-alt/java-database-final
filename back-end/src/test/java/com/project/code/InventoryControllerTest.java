package com.project.code;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.code.Controller.InventoryController;
import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ServiceClass serviceClass;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private InventoryRepository inventoryRepository;

    @Test
    void updateInventory_whenProductNotFound_returnsErrorMessage() throws Exception {
        Product p = new Product();
        p.setId(42L);
        CombinedRequest req = new CombinedRequest();
        req.setProduct(p);
        req.setInventory(new Inventory());

        when(serviceClass.ValidateProductId(42L)).thenReturn(false);

        mockMvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Id 42 not present in database"));
    }
}
