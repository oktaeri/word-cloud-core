
# WordCloud

This is one of three components of the WordCloud assignment.<br>
The app's functionality is described in the section below.

The Core component provides the REST API interface and sends data to
the worker via RabbitMQ for further processing.

Links to other components:
* Worker: <https://github.com/oktaeri/word-cloud-worker>
* Front: <https://github.com/oktaeri/word-cloud-front>
***

## About WordCloud
WordCloud is a simple text processing application. <br>
This section provides an overview of what the application does 
and what are the roles of the different components.

### Technologies used
- JDK 17
- Spring Boot
- PostgreSQL 16
- Flyway
- React
  - Bootstrap for easy styling
  - React Wordcloud from <https://www.npmjs.com/package/react-wordcloud>

## App flow
The user can upload a file (up to 100MB) and provide some options:
- **Minimum count (int)**: the minimum amount of times a word has 
to occur in the text to be included in the result
- **Filter common words (boolean):** setting this to true makes Worker filter 
out common words with no particular meaning (the, an, a, ...)
- **Filter custom words (string):** list of words separated by commas
that the user wants to filter out (can be used with/without common word filtering)

### Backend
Once the request is sent, Core generates a random 6-character token for the user. <br>
The user can use this token to retrieve their results.

The request is sent to RabbitMQ as a DTO, from where the Worker can consume and process it.<br>
The Worker then parses the file contents, adding each word and its respective occurrence count
into a map, while keeping in mind the user's options (if any were provided).<br>
The results get saved into the database.

The API (core) can then fetch the results from the DB and display them to 
the user in the frontend.

### Frontend
The user can insert their file and choose options from the form on the index page.<br>
File input is the only required field, the file also can not be bigger than 100MB.<br>
File size is validated in both frontend and backend.

If the user has to wait for their result, they are met with a pending screen. <br>
Once the result is ready, they have two options:
- View the result in a JSON list
- View the result as a word cloud

That's it! :)
***
## Running the app
1) Clone this (the Core) repository
2) Run `docker-compose.yml` 
3) Wait for the images to be pulled and started
4) Go to [`localhost:3000`](http://localhost:3000/) to use the website
5) Have fun!
> **CORS errors:**
> If you get CORS errors, give it a minute or two,
> the core needs to start up properly. :)

***
## Reflection

This was my first experience with microservices-oriented architecture. <br>
At first, it seemed pretty scary, as I had no prior knowledge of how to use RabbitMQ
and how to deal with microservices.

I found a neat course on RabbitMQ on [YouTube](https://www.youtube.com/watch?v=TvxhuAUJGUg&list=PLGRDMO4rOGcMh2fAMOnwuBMDa8PxiKWoN), 
which helped me out a lot in the beginning. :)

My appreciation for Spring Boot really grew during this project. 
Even if I did not know how to do everything at first, 
I thoroughly enjoyed the process of learning and improving myself.

The most challenging part was actually the frontend. The last
experience I had with React was spring 2023, which means I had forgotten most of it, 
but I was able to get back on track pretty quickly. :)

Thank you for this opportunity and the fun task!