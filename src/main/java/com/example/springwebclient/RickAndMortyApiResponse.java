package com.example.springwebclient;

import java.util.List;

public record RickAndMortyApiResponse(
        List<RickAndMortyCharacter> results,
        RickAndMortyApiResponseInfo info
) {
}
