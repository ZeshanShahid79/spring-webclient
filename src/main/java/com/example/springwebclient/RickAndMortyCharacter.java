package com.example.springwebclient;

public record RickAndMortyCharacter(
        int id,
        String name,
        String species,
        String status,
        RickAndMortyOrigin origin
) {
}
