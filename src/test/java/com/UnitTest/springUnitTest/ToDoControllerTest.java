package com.UnitTest.springUnitTest;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestConfig.class})

public class ToDoControllerTest {



    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    //@Test
    public void verifyAllToDoList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4))).andDo(print());
    }

    //@Test
    public void verifyToDoById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.text").value("Build the artifacts"))
                .andExpect(jsonPath("$.completed").value(false))
                .andDo(print());
    }

   // @Test
    public void verifyInvalidToDoArgument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/f").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("The request could not be understood by the server due to malformed syntax."))
                .andDo(print());
    }

    //@Test
    public void verifyInvalidToDoId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("ToDo doesn´t exist"))
                .andDo(print());
    }

    //@Test
    public void verifyNullToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/6").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("ToDo doesn´t exist"))
                .andDo(print());
    }

    //@Test
    public void verifyDeleteToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/todo/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("ToDo has been deleted"))
                .andDo(print());
    }

    //@Test
    public void verifyInvalidToDoIdToDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/todo/9").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("ToDo to delete doesn´t exist"))
                .andDo(print());
    }


   // @Test
    public void verifySaveToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.text").value("New ToDo Sample"))
                .andExpect(jsonPath("$.completed").value(false))
                .andDo(print());
    }

   // @Test
    public void verifyMalformedSaveToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"8\", \"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Payload malformed, id must not be defined"))
                .andDo(print());
    }

   // @Test
    public void verifyUpdateToDo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/todo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"text\" : \"New ToDo Text\", \"completed\" : \"false\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.completed").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("New ToDo Text"))
                .andExpect(jsonPath("$.completed").value(false))
                .andDo(print());
    }

    //@Test
    public void verifyInvalidToDoUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/todo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"idd\": \"8\", \"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("ToDo to update doesn´t exist"))
                .andDo(print());
    }



}