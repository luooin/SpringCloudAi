package com.cloud.springcloudai.controller;

/**
 * @author : luoC
 * @data : 2024/6/20 17:24
 * @description :
 */
/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;
import java.util.Map;


import com.alibaba.dashscope.audio.asr.transcription.TranscriptionParam;

import com.cloud.springcloudai.model.*;
import com.cloud.springcloudai.service.TongYiService;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TongYi models Spring Cloud Alibaba Controller.
 *
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 * @since 2023.0.0.0-RC1
 */

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class TongYiController {

    @Autowired
    @Qualifier("tongYiSimpleServiceImpl")
    private TongYiService tongYiSimpleService;

    @GetMapping
    public Object getMag( @RequestParam(value = "message", defaultValue = "Tell me a joke") String message,
            @RequestParam(value = "aiType", defaultValue = "translate")  String aiType ) {
        if ("translate".equals(aiType)) {// 千问
            return tongYiSimpleService.completion(message);
        } else if ("generateImage".equals(aiType)) {// 文生图
            ImageResponse imageResponse = tongYiSimpleService.genImg(message);
//            Image output = imageResponse.getResult().getOutput();
            return imageResponse.getResult().getOutput().getUrl();
        }
        return null;
    }

    @GetMapping("/example")
    public String completion(
            @RequestParam(value = "message", defaultValue = "Tell me a joke")
            String message
    ) {

        return tongYiSimpleService.completion(message);
    }

    @GetMapping("/stream")
    public Map<String, String> streamCompletion(
            @RequestParam(value = "message", defaultValue = "请告诉我西红柿炖牛腩怎么做？")
            String message
    ) {

        return tongYiSimpleService.streamCompletion(message);
    }


    @GetMapping("/output")
    public ActorsFilms generate(
            @RequestParam(value = "actor", defaultValue = "Jeff Bridges") String actor
    ) {

        return tongYiSimpleService.genOutputParse(actor);
    }


    @GetMapping("/prompt-tmpl")
    public AssistantMessage completion(@RequestParam(value = "adjective", defaultValue = "funny") String adjective,
                                       @RequestParam(value = "topic", defaultValue = "cows") String topic) {

        return tongYiSimpleService.genPromptTemplates(adjective, topic);
    }


    @GetMapping("/roles")
    public AssistantMessage generate(
            @RequestParam(value = "message", defaultValue = "Tell me about three famous pirates from the Golden Age of Piracy and why they did.  Write at least a sentence for each pirate.") String message,
            @RequestParam(value = "name", defaultValue = "bot") String name,
            @RequestParam(value = "voice", defaultValue = "pirate") String voice) {

        return tongYiSimpleService.genRole(message, name, voice);
    }


    @GetMapping("/stuff")
    public Completion completion(@RequestParam(value = "message",
            defaultValue = "Which athletes won the mixed doubles gold medal in curling at the 2022 Winter Olympics?") String message,
                                 @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {

        return tongYiSimpleService.stuffCompletion(message, stuffit);
    }


    @GetMapping("/img")
    public ImageResponse genImg(@RequestParam(value = "prompt",
            defaultValue = "Painting a picture of blue water and blue sky.") String imgPrompt) {

        return tongYiSimpleService.genImg(imgPrompt);
    }


    @GetMapping("/audio")
    public String genAudio(@RequestParam(value = "prompt",
            defaultValue = "你好，Spring Cloud Alibaba AI 框架！") String prompt) {

        return tongYiSimpleService.genAudio(prompt);
    }


    /**
     * audio transcription. Support urls audio resource.
     * {@link Resource}
     * {@link TranscriptionParam}
     *
     * @param url audio url.
     * @return transcription result, is String type.
     */
    @GetMapping("/audio/transcription")
    public String audioTranscription(@RequestParam(value = "audioUrls",
            defaultValue = "https://dashscope.oss-cn-beijing.aliyuncs.com/samples/audio/paraformer/realtime_asr_example.wav") String url) {

        return tongYiSimpleService.audioTranscription(url);
    }


}
