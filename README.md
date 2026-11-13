# UC00619 — Sessão 15 — Exercício 1 — Solução

Projeto `library-jpa` com categorias (N–N) e avaliações (1–N). Spring Boot 4, Java 21.

APIs:
   - `GET /api/books/1/details` → 200, categorias `["Best practices","Programming"]`, 2 avaliações, média 4,5
   - `POST /api/books/3/reviews` com `{"rating":4,"comment":"Dense but great"}` → 201, id 4
   - `POST /api/books/99/reviews` → 404 · rating 7 → 400 `VALIDATION_FAILED`
   - `GET /api/books/summary` → 1 select no log (trocar por `findAll()` no service para ver os 5 select do N+1)
   - `GET /api/categories/Java/books` → `["Effective Java","Head First Java"]`
