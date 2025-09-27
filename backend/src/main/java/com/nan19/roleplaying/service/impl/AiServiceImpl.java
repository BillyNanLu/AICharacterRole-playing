package com.nan19.roleplaying.service.impl;

import com.nan19.roleplaying.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AiServiceImpl implements AiService {

    @Value("${ai.asr.endpoint}")
    private String asrEndpoint;
    @Value("${ai.asr.key}")
    private String asrKey;

    @Value("${ai.tts.endpoint}")
    private String ttsEndpoint;
    @Value("${ai.tts.key}")
    private String ttsKey;

    @Value("${ai.llm.endpoint}")
    private String llmEndpoint;
    @Value("${ai.llm.key}")
    private String llmKey;

    @Override
    public String speechToText(MultipartFile audioFile) {
        // TODO: 用 RestTemplate / OkHttp 调 asrEndpoint
        // 发送 multipart 请求 audioFile
        // 返回识别出的文字
        return "这是ASR识别的文字（示例）";
    }

    @Override
    public String llmGenerate(String prompt) {
        // TODO: 用 llmKey 调大模型接口，返回文本
        return "这是大模型回复（示例），prompt=" + prompt;
    }

    @Override
    public String textToSpeechBase64(String text) {
        // TODO: 用 ttsKey 调 TTS 接口，返回音频 Base64
        return "";
    }
}