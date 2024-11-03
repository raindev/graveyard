#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stddef.h>
#include <sys/time.h>

typedef struct node {
    struct node *next;
    char buffer[20];
} node_t;

#define NODES                     1000000

node_t *new_node(node_t *prev, const char *payload, size_t size) {
    node_t *n = (node_t *) malloc(sizeof(node_t));
    if (n == NULL) {
        printf("Error allocating %ld bytes for struct node!", sizeof(node_t));
        exit(1);
    }
    // Copy the payload into the node
    memcpy(n->buffer, payload, size);
    n->next = NULL;

    if (prev) {
        prev->next = n;
    }
    return n;
}

void free_nodes(node_t *n) {
    if (!n) return;
    while (n->next) {
        node_t *next = n->next;
        free(n);
        n = next;
    }
}

int main() {
    const char text[] = "This is a text";
    node_t *node0 = new_node(NULL, text, strlen(text));

    node_t * node = node0;
    struct timeval start, end;
    gettimeofday(&start, NULL);
    for (int i=1; i < NODES; i++) {
        node = new_node(node, text, strlen(text));
    }
    gettimeofday(&end, NULL);

    int duration = ((end.tv_sec - start.tv_sec) * 1000000) + (end.tv_usec - start.tv_usec);
    printf("%d Nodes creation took %d us\n", NODES, duration);

    free_nodes(node0);
}
