package com.flyingtechs.studentManagement.controller.impl;

import com.flyingtechs.studentManagement.controller.impl.CourseControllerImpl;
import com.flyingtechs.studentManagement.dto.CourseDTO;
import com.flyingtechs.studentManagement.model.Course;
import com.flyingtechs.studentManagement.service.CourseService;
import com.flyingtechs.userManagement.controller.impl.CustomUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CourseControllerImplTest1 {
    private static final String ENDPOINT_URL = "/courses";

    @InjectMocks
    private CourseControllerImpl courseController;

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.courseController).build();
    }

    @Test
    public void getAll() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);

        Course course2 = new Course();
        course2.setId(2L);

        Mockito.when(courseService.findAll()).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Course course = new Course();
        course.setId(1L);

        Mockito.when(courseService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(course));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(courseService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(courseService);
    }

    @Test
    public void save() throws Exception {
        Course course = new Course();

        Mockito.when(courseService.save(ArgumentMatchers.any(Course.class))).thenReturn(course);

        mockMvc.perform(
                MockMvcRequestBuilders.post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(CustomUtils.asJsonString(new CourseDTO(course)))
                        .accept((MediaType) MockMvcResultMatchers.status().isCreated()));
        Mockito.verify(courseService, Mockito.times(1)).save(ArgumentMatchers.any(Course.class));
        Mockito.verifyNoMoreInteractions(courseService);
    }

    @Test
    public void update() throws Exception {
        Course course = new Course();

        Mockito.when(courseService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(course);

        mockMvc.perform(
                MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(CustomUtils.asJsonString(new CourseDTO(course)))
                        .accept((MediaType) MockMvcResultMatchers.status().isOk()));
        Mockito.verify(courseService, Mockito.times(1)).update(ArgumentMatchers.any(Course.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(courseService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(courseService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(courseService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(courseService);
    }
}