package main

import "context"
import "fmt"
import "html"
import "log"
import "net/http"

func main() {
	respond := make(chan interface{})
	server(respond)

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	request := make(chan interface{})
	client(ctx, request)

	request <- 1
	respond <- 1

}

func server(respond chan interface{}) {
	http.HandleFunc("/hi", func(w http.ResponseWriter, r *http.Request) {
		log.Print("handler invoked")
		<-respond
		fmt.Fprintf(w, "Hello, %q", html.EscapeString(r.URL.Path))
		log.Print("response sent")
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
		log.Print("request sent")
		if err != nil {
			log.Fatal(err)
		}
		log.Print("response received", res)
	}()
}
