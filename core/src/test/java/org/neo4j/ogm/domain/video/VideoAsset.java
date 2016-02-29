package org.neo4j.ogm.domain.video;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class VideoAsset {
    @GraphId
    private Long gid;
    private String title;
    private String caption;
    private String url;
    private Integer runtimeSecs;

    private String playbackUrl;

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public VideoAsset setPlaybackUrl(String playbackUrl) {
        this.playbackUrl = playbackUrl;
        return this;
    }

    public Integer getRuntimeSecs() {
        return runtimeSecs;
    }

    public VideoAsset setRuntimeSecs(Integer runtimeSecs) {
        this.runtimeSecs = runtimeSecs;
        return this;
    }

    public Long getGid() {
        return gid;
    }

    public VideoAsset setGid(Long gid) {
        this.gid = gid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VideoAsset setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public VideoAsset setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public VideoAsset setCaption(String caption) {
        this.caption = caption;
        return this;
    }
}
