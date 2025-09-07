# Pokedex API

A simple Spring Boot project that exposes Pokémon information using the [PokéAPI](https://pokeapi.co/) and [Fun Translations API](https://funtranslations.com/)

---

## Requirements

- [Docker](https://www.docker.com/products/docker-desktop)
- Free port 8080
- [Git](https://git-scm.com/)

> No need to have Java or Gradle installed locally.

---

## How to Run
0. Clone the Repository
```bash
git clone https://github.com/manuelpagliuca/pokedex.git
```
1. Enter the project repository
```bash
cd pokedex
```

2. Start the Docker daemon (open the application or execute the command)
- Make sure the Docker daemon is running (e.g., Docker Desktop on Windows/Mac, or `dockerd` on Linux).

3. Build the Docker image:

```bash
docker build -t pokedex-app .
```

4. Run the container:
```bash
docker run -p 8080:8080 pokedex-app
```

The API will be available at: http://localhost:8080

## API Endpoints

## API Endpoints Overview

| Endpoint                        | Description                          |
|---------------------------------|--------------------------------------|
| `GET /pokemon/{name}`           | Get Pokémon information (English description). |
| `GET /pokemon/translate/{name}` | Get translated Pokémon information (Yoda/Shakespeare description). |


### GET /pokemon/{name}
This API provides a GET endpoint that accepts a Pokémon’s name as a path parameter.

- The description is in English.

Example:
```
curl http://localhost:8080/pokemon/mewtwo
```

The response will include (as JSON):

* **name**: the Pokémon’s name
* **description**: a short description of the Pokémon
* **habitat**: the natural habitat of the Pokémon
* **legendary**: whether the Pokémon is legendary (true/false)

```json
{
    "name": "mewtwo",
    "description": "Created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
    "habitat": "rare",
    "isLegendary": true
}
```

### GET /pokemon/translate/{name}

This API provides a GET endpoint that accepts a Pokemon's name as a path parameter.

Example:
```
curl http://localhost:8080/pokemon/zubat
```

It will return almost the same information as [GET /pokemon/{name}](#get-pokemonname), the difference lies
in the **description** field.

In the case the **habitat** of the requested Pokémon is `cave`, or it is a **legendary**, it will use the
[Yoda translation](https://funtranslations.com/yoda), otherwise the [Shakespeare translation](https://funtranslations.com/shakespeare).

```json
{
    "name": "mewtwo",
    "description": "Created by a scientist after years of horrific gene splicing and dna engineering experiments,  it was.",
    "isLegendary": true,
    "habitat": "rare"
}
```

## Testing

- All unit tests are included.
- Client tests use mocked HTTP responses.
- Service tests verify description extraction, habitat parsing, and Pokémon-not-found handling.

## Production Notes

In a real-world scenario, I would recommend:

- **Caching** PokéAPI requests ([Redis](https://redis.io/), [caffeine](https://github.com/ben-manes/caffeine), ...)
- **Retry handling** for network errors ([Resilience4j](https://resilience4j.readme.io/docs/getting-started), ...)
- More detailed and **centralized logging** ([Grafana](https://grafana.com/docs/grafana/latest/), [Prometheus](https://prometheus.io/), ...)
- **Rate-limiting** handling for third-party APIs ([Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway), [bucket4j](https://github.com/bucket4j/bucket4j), ...)
  - The API provided from Pokémon is rate limited by 10 request in an hour
- **Scalability**  
  - Deploy using orchestration platforms ([Docker Compose](https://docs.docker.com/compose/), [Kubernetes](https://kubernetes.io/), ...).  
  - Horizontal scaling could be applied easily since the service is **_stateless_** (especially with external caching).