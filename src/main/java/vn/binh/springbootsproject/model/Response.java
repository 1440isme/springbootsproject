package vn.binh.springbootsproject.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Boolean status;
    private String message;
    private Object body;
}