package com.example.umc10th.global.api;

public record ApiResponse<T>(
	boolean success,
	String code,
	String message,
	T result
) {
}
