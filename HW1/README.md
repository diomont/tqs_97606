# TQS MW1 - Midterm Assignment

## How to run

### API

In the `/api` directory:

```bash
mvn spring-boot:run
```

### Webapp

In the `/webapp` directory:

First, it is recommended to use a virtual environment, as such:

```bash
python3 -m venv venv  # if no virtual env exists
source venv/bin/activate
```

Then run the following commands to install the dependencies and run the server:

```bash
pip install -r requirements.txt
flask run
```

## Tests

### Cache

The cache should:
- When a get for X is made, then no result should be found, X should added to the cache and misses should be 1
- Given the cache has X, when a get for X is made, then X should be retrieved and hits should be 1
- Given the cache has X, when a get for X is made after its time-to-live has passed, then no result should be found, X should added to the cache and misses should be 1
