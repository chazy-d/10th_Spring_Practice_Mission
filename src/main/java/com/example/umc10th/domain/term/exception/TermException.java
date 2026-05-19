package com.example.umc10th.domain.term.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;

public class TermException extends ProjectException {

	public TermException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
