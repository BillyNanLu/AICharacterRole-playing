package com.nan19.roleplaying.service;

import org.springframework.web.multipart.MultipartFile;

public interface AiService {
    String speechToText(MultipartFile audioFile);           // ASR
    String llmGenerate(String prompt);                      // 大模型
    String textToSpeechBase64(String text);                 // TTS
}