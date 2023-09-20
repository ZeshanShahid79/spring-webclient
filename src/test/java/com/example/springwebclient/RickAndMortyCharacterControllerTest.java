package com.example.springwebclient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RickAndMortyCharacterControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry registry) {
        registry.add("rickandmorty.api.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void getCharacters() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                        "results": [
                        {
                                "id": 20,
                                "name": "Ants in my Eyes Johnson",
                                "species": "Human",
                                "status": "unknown",
                                "origin": {
                                    "name": "unknown",
                                    "url": ""
                                }
                        }
                        ]
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [
                                {
                                    "id": 20,
                                    "name": "Ants in my Eyes Johnson",
                                    "species": "Human",
                                    "status": "unknown",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    }
                                }
                            ]
                        """));
    }

    @Test
    void getCharacterById() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                                "id": 20,
                                "name": "Ants in my Eyes Johnson",
                                "species": "Human",
                                "status": "unknown",
                                "origin": {
                                    "name": "unknown",
                                    "url": ""
                                }
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters/20"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id": 20,
                                    "name": "Ants in my Eyes Johnson",
                                    "species": "Human",
                                    "status": "unknown",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    }
                                }
                        """));
    }

    @Test
    void getCharactersByStatus() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                        "results": [
                        {
                                "id": 20,
                                "name": "Ants in my Eyes Johnson",
                                "species": "Human",
                                "status": "unknown",
                                "origin": {
                                    "name": "unknown",
                                    "url": ""
                                }
                        }
                        ]
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters?status=unkown"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [
                                {
                                    "id": 20,
                                    "name": "Ants in my Eyes Johnson",
                                    "species": "Human",
                                    "status": "unknown",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    }
                                }
                            ]
                        """));
    }

    @Test
    void getStatisticForSpecies() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                        "results": [
                        {
                                "id": 20,
                                "name": "Ants in my Eyes Johnson",
                                "species": "Human",
                                "status": "unknown",
                                "origin": {
                                    "name": "unknown",
                                    "url": ""
                                }
                        }
                        ]
                        }
                        """));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters?status=unkown&species=human"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [
                                {
                                    "id": 20,
                                    "name": "Ants in my Eyes Johnson",
                                    "species": "Human",
                                    "status": "unknown",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    }
                                }
                            ]
                        """));
    }
}
