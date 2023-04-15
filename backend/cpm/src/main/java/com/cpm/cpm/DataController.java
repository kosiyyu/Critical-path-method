package com.cpm.cpm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class DataController {

    @GetMapping
    String getData() {

        DataDto dummyGraph = new DataDto(
                List.of(
                        new Node(1, "Node1", "#1FED75"),
                        new Node(2, "Node2", "#1F84ED"),
                        new Node(3, "Node3", "#1F84ED"),
                        new Node(4, "Node4", "#1F84ED"),
                        new Node(5, "Node5", "#1F84ED"),
                        new Node(6, "Node6", "#1F84ED"),
                        new Node(7, "Node7", "#1F84ED"),
                        new Node(8, "Node8", "#1FED75")
                ),
                List.of(
                        new Link(1, 2, "Link1", "black"),
                        new Link(1, 3, "Link2", "black"),
                        new Link(2, 3, "Link3", "gray"),
                        new Link(3, 4, "Link4", "black"),
                        new Link(4, 6, "Link5", "black"),
                        new Link(4, 5, "Link6", "black"),
                        new Link(5, 6, "Link7", "gray"),
                        new Link(5, 7, "Link8", "black"),
                        new Link(6, 7, "Link9", "black"),
                        new Link(7, 8, "Link10", "black")
                )
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try{
            json = objectMapper.writeValueAsString(dummyGraph);
        }catch (JsonProcessingException e){

        }

        return json != null ? json : "";
    }
}
