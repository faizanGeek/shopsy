package com.luv2code.ecommerce.util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorInfo {

	private String errorMsg;

	private Integer errorCode;

	private LocalDateTime timestamp;
}
