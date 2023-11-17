package com.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppOutingUserTimeDTO {

   @Id
   private String NAME ;
   private LocalDateTime OUTING;
   private LocalDateTime RETURN_;

}