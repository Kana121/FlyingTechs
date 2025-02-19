package com.flyingtechs.studentManagement.controller.impl;

import com.flyingtechs.studentManagement.dto.TestResultDTO;
import com.flyingtechs.studentManagement.model.TestResult;
import com.flyingtechs.studentManagement.service.TestResultService;
import com.flyingtechs.userManagement.controller.impl.CustomUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestResultControllerImplTest {
    private static final String ENDPOINT_URL = "/test-results";

    @InjectMocks
    private TestResultControllerImpl testResultController;

    @MockBean
    private TestResultService testResultService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.testResultController).build();
    }

    @Test
    public void getAll() throws Exception {
        TestResult testResult1 = new TestResult();
        testResult1.setId(1L);

        TestResult testResult2 = new TestResult();
        testResult2.setId(2L);

        Mockito.when(testResultService.findAll()).thenReturn(Arrays.asList(testResult1, testResult2));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void getById() throws Exception {
        TestResult testResult = new TestResult();
        testResult.setId(1L);

        Mockito.when(testResultService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(testResult));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(testResultService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(testResultService);
    }

    @Test
    public void save() throws Exception {
        TestResult testResult = new TestResult();

        Mockito.when(testResultService.save(ArgumentMatchers.any(TestResult.class))).thenReturn(testResult);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(new TestResultDTO(testResult)))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(testResultService, Mockito.times(1)).save(ArgumentMatchers.any(TestResult.class));
        Mockito.verifyNoMoreInteractions(testResultService);
    }

    @Test
    public void update() throws Exception {
        TestResult testResult = new TestResult();

        Mockito.when(testResultService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(testResult);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(new TestResultDTO(testResult)))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(testResultService, Mockito.times(1)).update(ArgumentMatchers.any(TestResult.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(testResultService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(testResultService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(testResultService, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(testResultService);
    }
}