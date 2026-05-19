package com.example.umc10th.domain.category.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;

public class CategoryException extends ProjectException {

	public CategoryException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
