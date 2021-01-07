#!/usr/bin/env python3

import random

def subset(backends, client_id, subset_size):
  subset_count = len(backends) // subset_size

  round = client_id // subset_count
  random.seed(round)
  backends = backends.copy()
  random.shuffle(backends)

  subset_id = client_id % subset_count

  start = subset_id * subset_size
  return backends[start:start + subset_size]

# for the perfect distribution both the number of backends and the number of
# clients should be divisible by subset_size
backends = list(range(35))
clients = list(range(15))
subset_size = 5

connections = []
for client in clients:
    client_connections = subset(backends, client, subset_size)
    print("Client %s connections" % client)
    print(client_connections)
    connections += client_connections

connections_by_backend = {}
for backend in connections:
    if backend in connections_by_backend:
        connections_by_backend[backend] += 1
    else:
        connections_by_backend[backend] = 1

print("Number of connectiosn per backend")
print(connections_by_backend)
