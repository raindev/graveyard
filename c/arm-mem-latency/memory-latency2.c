#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stddef.h>
#include <sys/time.h>

typedef struct node {
    struct node *next;
    char buffer[24];
} node_t;

#define NODES                     1000000
#define SIMPLE_ALLOCATOR_SIZE     100000000UL

static void *start_ = NULL;
static void *cur_ = NULL;
static void *end_ = NULL;
static size_t counter_ = 0;

static void init_alloc() {
    if (start_ == NULL) {
        start_ = malloc(SIMPLE_ALLOCATOR_SIZE);
        if (start_ == NULL) {
            printf("Error allocating %ld bytes for allocator!", SIMPLE_ALLOCATOR_SIZE);
            exit(1);
        }
        memset(start_, 0, SIMPLE_ALLOCATOR_SIZE);
        cur_ = start_;
        end_ = start_ + SIMPLE_ALLOCATOR_SIZE;
    }
}

static void* simple_alloc(size_t size) {
    if (cur_ + size <= end_) {
      void *ptr = cur_;
      cur_ += size;
      counter_++;
      return ptr;
    } else {
       printf("Error allocating %ld bytes!", size);
    }
    return NULL;
}

node_t *new_node(node_t *prev, const char *payload, size_t size) {
    node_t *n = (node_t *) simple_alloc(sizeof(node_t));
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

int main() {
    init_alloc();
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

    free(start_);
}
