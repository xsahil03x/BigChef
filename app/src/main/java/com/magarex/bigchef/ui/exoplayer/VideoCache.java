package com.magarex.bigchef.ui.exoplayer;

import android.content.Context;

import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class VideoCache {
    private static SimpleCache sDownloadCache;

    public static SimpleCache getInstance(Context context) {
        if (sDownloadCache == null)
            sDownloadCache = new SimpleCache(new File(context.getCacheDir(), "BigChefVideoCache"), new NoOpCacheEvictor());
        return sDownloadCache;
    }
}