package com.example.umc10th.domain.region.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.exception.ProjectException;

public class RegionException extends ProjectException {

	public RegionException(BaseErrorCode errorCode) {
		super(errorCode);
	}
}
