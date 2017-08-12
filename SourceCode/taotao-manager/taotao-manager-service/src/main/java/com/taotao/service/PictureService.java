package com.taotao.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	Map uploadPicture(MultipartFile uploadFile);
}
