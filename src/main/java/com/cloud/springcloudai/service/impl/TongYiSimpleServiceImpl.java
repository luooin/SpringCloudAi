package com.cloud.springcloudai.service.impl;

import com.alibaba.cloud.ai.tongyi.audio.api.SpeechClient;
import com.alibaba.dashscope.audio.tts.SpeechSynthesisAudioFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/**
 * @author : luoC
 * @data : 2024/6/20 17:22
 * @description :
 */
@Service
@Slf4j
public class TongYiSimpleServiceImpl extends AbstractTongYiServiceImpl {

    private final ChatClient chatClient;

    private final StreamingChatClient streamingChatClient;

    private final ImageClient imageClient;

    private final SpeechClient speechClient;

    @Autowired
    public TongYiSimpleServiceImpl(ChatClient chatClient, StreamingChatClient streamingChatClient,ImageClient imageClient,SpeechClient speechClient) {
        this.chatClient = chatClient;
        this.streamingChatClient = streamingChatClient;
        this.imageClient = imageClient;
        this.speechClient = speechClient;
    }

    @Override
    public String completion(String message) {

        Prompt prompt = new Prompt(new UserMessage(message));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    @Override
    public Map<String, String> streamCompletion(String message) {

        StringBuilder fullContent = new StringBuilder();

        streamingChatClient.stream(new Prompt(message))
                .flatMap(chatResponse -> Flux.fromIterable(chatResponse.getResults()))
                .map(content -> content.getOutput().getContent())
                .doOnNext(fullContent::append)
                .last()
                .map(lastContent -> Map.of(message, fullContent.toString()))
                .block();

        log.info(fullContent.toString());

        return Map.of(message, fullContent.toString());
    }

    @Override
    public ImageResponse genImg(String imgPrompt) {
        var prompt = new ImagePrompt(imgPrompt);
        return imageClient.call(prompt);
    }

    @Override
    public String genAudio(String text) {
        var resWAV = speechClient.call(text);
        // save的代码省略，就是将音频保存到本地而已
        return save(resWAV, SpeechSynthesisAudioFormat.WAV.getValue());
    }

    private String save(ByteBuffer byteBuffer, String value) {

        byteBuffer.flip();

        File file = new File("wavFile/output.wav");
        try (FileOutputStream fos = new FileOutputStream(file);
             FileChannel fileChannel = fos.getChannel()) {
            // 写入操作将在这里进行
            int bytesWritten = fileChannel.write(byteBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }


}
