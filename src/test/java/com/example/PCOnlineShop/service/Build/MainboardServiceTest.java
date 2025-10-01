package com.example.PCOnlineShop.service.Build;

import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.repository.build.MainboardRepository;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CpuService;
import com.example.PCOnlineShop.service.build.MainboardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(CpuService.class)
public class MainboardServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MainboardRepository mainboardRepository;

    @Test
    void testGetMainboardById() {
        Mainboard fake = new Mainboard();
        fake.setProductId(1);
        fake.setSocket("AM4");

        Mockito.when(mainboardRepository.findById(1))
                .thenReturn(Optional.of(fake));
        MainboardService mainboardService = new MainboardService(mainboardRepository);
        Mainboard result = mainboardService.getMainboardById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("AM4", result.getSocket());
    }
}
