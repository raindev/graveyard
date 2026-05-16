package main

import "context"
import "fmt"
import "html"
import "log"
import "net/http"

func main() {
	respond := make(chan interface{})
	server(respond)

	ctx, cancel_req := context.WithCancel(context.Background())

	request := make(chan interface{})
	client(ctx, request)

	request <- 1 // send request
	respond <- 1 // send response

	<-respond    // response sent
	<-request    // headers received
	request <- 1 // read body
	<-request    // body read
	cancel_req()
}

func server(respond chan interface{}) {
	http.HandleFunc("/hi", func(w http.ResponseWriter, r *http.Request) {
		log.Print("handler invoked")
		<-respond
		fmt.Fprintf(w, "Hello, %q", html.EscapeString(r.URL.Path))
		log.Print("response sent")
		respond <- 1
	})
	log.Print("handler registered")
	go func() {
		log.Fatal(http.ListenAndServe(":8080", nil))
	}()
	log.Print("server started")
}

func client(ctx context.Context, request chan interface{}) {
	req, err := http.NewRequestWithContext(ctx, "get", "http://localhost:8080/hi", nil)
	if err != nil {
		log.Fatal(err)
	}
	client := http.Client{}
	go func() {
		<-request
		log.Print("sending request")
		res, err := client.Do(req)
		if err != nil {
			log.Fatal("request failed ", err)
		}
		log.Print("response headers received")
		request <- 1

		log.Print("reading body")
		<-request
		log.Print("response: ", res)
		request <- 1
		log.Print("client done")
	}()
}
