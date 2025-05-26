package com.paccy.springbootcaching.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse <T>{
    private final LocalDateTime timestamp;

    private T data;
    private HttpStatus status;
    private String message;

    public ApiResponse(String message,HttpStatus status,T data){
        this.timestamp = LocalDateTime.now();
        this.message=message;
        this.status=status;
        this.data=data;
    }


    public ResponseEntity<ApiResponse<T>> toResponseEntity(){
     assert  status !=null;
     return ResponseEntity.status(status).body(this);
    }
}
