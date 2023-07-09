package com.santiago.posada.routes;

import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskRoutes {

    @Autowired
    private ToDoService service;

    @Bean
    public RouterFunction<ServerResponse> getTasks(){
        return route(GET("route/get/all"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.getTasks(), ToDo.class)));
    }

    //Generar un tres router functions
    //Post para guardar una tarea
    @Bean
    public RouterFunction<ServerResponse> saveTask(){
        return route(POST("route/save/{task}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.addTask(request.pathVariable("task")), ToDo.class))
                    );
    }

    //Put para actualizar
    @Bean
    public RouterFunction<ServerResponse> updateTask(){
        return route(PUT("route/update/{id}/{task}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.updateTask(request.pathVariable("id"),request.pathVariable("task")),ToDo.class))
                );
    }

    //Delete para eliminar una tarea.
    @Bean
    public RouterFunction<ServerResponse> deleteTask(){
        return route(DELETE("route/delete/{id}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.deleteTask(request.pathVariable("id")),Void.class))
        );
    }


}
