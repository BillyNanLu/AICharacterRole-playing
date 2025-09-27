package com.nan19.roleplaying.controller;

import com.nan19.roleplaying.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private AiService aiService;

    @PostMapping("/text")
    public Map<String, Object> chatText(@RequestBody Map<String, Object> body) {
        String content = (String) body.get("content");
        String role = (String) body.get("role");
        String skill = (String) body.get("skill");

        // TODO 这里先返回假数据，后面再接入AI
        Map<String, Object> res = new HashMap<>();
        res.put("answer", "这是AI模拟回复: " + content + " (角色: " + role + " 技能: " + skill + ")");
        return res;
    }

    @PostMapping("/voice")
    public Map<String, Object> chatVoice(
            @RequestParam("role") String role,
            @RequestParam("skill") String skill,
            @RequestParam("audio") MultipartFile audioFile) {

        // 1. ASR
        String userText = aiService.speechToText(audioFile);

        // 2. 拼接 prompt 调大模型
        String prompt = "以" + role + "的身份，使用" + skill + "回答：" + userText;
        String assistantText = aiService.llmGenerate(prompt);

        // 3. TTS
        String ttsBase64 = aiService.textToSpeechBase64(assistantText);

        Map<String, Object> res = new HashMap<>();
        res.put("userText", userText);
        res.put("assistantText", assistantText);
        res.put("ttsAudioBase64", ttsBase64);
        return res;
    }
}