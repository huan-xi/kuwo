package com.huanxi.music;

import com.mpatric.mp3agic.*;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestMp3 {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {
        String filename = "/Users/huangjiawei/IdeaProjects/music/data/music/周杰伦/周杰伦的床边故事/告白气球.mp3";
        String filename2 = "/Users/huangjiawei/IdeaProjects/music/data/music/周杰伦/周杰伦的床边故事/告白气球21.mp3";
        Mp3File mp3file = new Mp3File(filename);

        ID3v2 v2 = new ID3v24Tag();
        v2.setArtist("周杰伦");
        mp3file.setId3v2Tag(v2);
        mp3file.save(filename2);
        System.out.println(mp3file.getId3v1Tag().getTitle());
    }
}
